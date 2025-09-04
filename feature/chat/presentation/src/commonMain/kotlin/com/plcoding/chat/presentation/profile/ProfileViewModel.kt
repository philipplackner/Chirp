package com.plcoding.chat.presentation.profile

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.error_current_password_equal_to_new_one
import chirp.feature.chat.presentation.generated.resources.error_current_password_incorrect
import chirp.feature.chat.presentation.generated.resources.error_invalid_file_type
import com.plcoding.chat.domain.participant.ChatParticipantRepository
import com.plcoding.chat.domain.participant.ChatParticipantService
import com.plcoding.core.domain.auth.AuthService
import com.plcoding.core.domain.auth.SessionStorage
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.onFailure
import com.plcoding.core.domain.util.onSuccess
import com.plcoding.core.domain.validation.PasswordValidator
import com.plcoding.core.presentation.util.UiText
import com.plcoding.core.presentation.util.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: AuthService,
    private val chatParticipantRepository: ChatParticipantRepository,
    private val sessionStorage: SessionStorage,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProfileState())
    val state = combine(
        _state,
        sessionStorage.observeAuthInfo()
    ) { currentState, authInfo ->
        if(authInfo != null) {
            currentState.copy(
                username = authInfo.user.username,
                userInitials = authInfo.user.username.take(2),
                emailTextState = TextFieldState(initialText = authInfo.user.email),
                profilePictureUrl = authInfo.user.profilePictureUrl,
            )
        } else currentState
    }
        .onStart {
            if (!hasLoadedInitialData) {
                observeCanChangePassword()
                fetchLocalParticipantDetails()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProfileState()
        )

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnChangePasswordClick -> changePassword()
            is ProfileAction.OnToggleCurrentPasswordVisibility -> toggleCurrentPasswordVisibility()
            is ProfileAction.OnToggleNewPasswordVisibility -> toggleNewPasswordVisibility()
            is ProfileAction.OnPictureSelected -> uploadProfilePicture(action.bytes, action.mimeType)
            is ProfileAction.OnDeletePictureClick -> showDeleteConfirmation()
            is ProfileAction.OnConfirmDeleteClick -> deleteProfilePicture()
            is ProfileAction.OnDismissDeleteConfirmationDialogClick -> dismissDeleteConfirmation()
            else -> Unit
        }
    }

    private fun deleteProfilePicture() {
        if(state.value.isDeletingImage && state.value.profilePictureUrl == null) {
            return
        }

        _state.update { it.copy(
            isDeletingImage = true,
            imageError = null,
            showDeleteConfirmationDialog = false
        ) }

        viewModelScope.launch {
            chatParticipantRepository
                .deleteProfilePicture()
                .onSuccess {
                    _state.update { it.copy(
                        isDeletingImage = false
                    ) }
                }
                .onFailure { error ->
                    _state.update { it.copy(
                        imageError = error.toUiText(),
                        isDeletingImage = false
                    ) }
                }
        }
    }

    private fun dismissDeleteConfirmation() {
        _state.update { it.copy(
            showDeleteConfirmationDialog = false
        ) }
    }

    private fun showDeleteConfirmation() {
        _state.update { it.copy(
            showDeleteConfirmationDialog = true
        )}
    }

    private fun uploadProfilePicture(bytes: ByteArray, mimeType: String?) {
        if(state.value.isUploadingImage) {
            return
        }

        if(mimeType == null) {
            _state.update { it.copy(
                imageError = UiText.Resource(Res.string.error_invalid_file_type)
            ) }
            return
        }

        _state.update { it.copy(
            isUploadingImage = true,
            imageError = null
        ) }

        viewModelScope.launch {
            chatParticipantRepository
                .uploadProfilePicture(
                    imageBytes = bytes,
                    mimeType = mimeType
                )
                .onSuccess {
                    _state.update { it.copy(
                        isUploadingImage = false,
                    ) }
                }
                .onFailure { error ->
                    _state.update { it.copy(
                        imageError = error.toUiText(),
                        isUploadingImage = false
                    ) }
                }
        }
    }

    private fun fetchLocalParticipantDetails() {
        viewModelScope.launch {
            chatParticipantRepository.fetchLocalParticipant()
        }
    }

    private fun toggleCurrentPasswordVisibility() {
        _state.update { it.copy(
            isCurrentPasswordVisible = !it.isCurrentPasswordVisible
        ) }
    }

    private fun toggleNewPasswordVisibility() {
        _state.update { it.copy(
            isNewPasswordVisible = !it.isNewPasswordVisible
        ) }
    }

    private fun observeCanChangePassword() {
        val isCurrentPasswordValidFlow = snapshotFlow {
            state.value.currentPasswordTextState.text.toString()
        }.map { it.isNotBlank() }.distinctUntilChanged()

        val isNewPasswordValidFlow = snapshotFlow {
            state.value.newPasswordTextState.text.toString()
        }.map {
            PasswordValidator.validate(it).isValidPassword
        }.distinctUntilChanged()

        combine(
            isCurrentPasswordValidFlow,
            isNewPasswordValidFlow
        ) { isCurrentValid, isNewValid ->
            _state.update { it.copy(
                canChangePassword = isCurrentValid && isNewValid
            ) }
        }.launchIn(viewModelScope)
    }

    private fun changePassword() {
        if(!state.value.canChangePassword && state.value.isChangingPassword) {
            return
        }

        _state.update { it.copy(
            isChangingPassword = true,
            isPasswordChangeSuccessful = false
        ) }
        viewModelScope.launch {
            val currentPassword = state.value.currentPasswordTextState.text.toString()
            val newPassword = state.value.newPasswordTextState.text.toString()
            authService
                .changePassword(
                    currentPassword = currentPassword,
                    newPassword = newPassword
                )
                .onSuccess {
                    state.value.currentPasswordTextState.clearText()
                    state.value.newPasswordTextState.clearText()

                    _state.update { it.copy(
                        isChangingPassword = false,
                        newPasswordError = null,
                        isNewPasswordVisible = false,
                        isCurrentPasswordVisible = false,
                        isPasswordChangeSuccessful = true
                    ) }
                }
                .onFailure { error ->
                    val errorMessage = when(error) {
                        DataError.Remote.UNAUTHORIZED -> {
                            UiText.Resource(Res.string.error_current_password_incorrect)
                        }
                        DataError.Remote.CONFLICT -> {
                            UiText.Resource(Res.string.error_current_password_equal_to_new_one)
                        }
                        else -> error.toUiText()
                    }
                    _state.update { it.copy(
                        newPasswordError = errorMessage,
                        isChangingPassword = false
                    ) }
                }
        }
    }

}