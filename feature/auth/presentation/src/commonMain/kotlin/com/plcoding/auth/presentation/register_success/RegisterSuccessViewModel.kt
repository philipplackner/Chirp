package com.plcoding.auth.presentation.register_success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.auth.AuthService
import com.plcoding.core.domain.util.onFailure
import com.plcoding.core.domain.util.onSuccess
import com.plcoding.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSuccessViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<RegisterSuccessEvent>()
    val events = eventChannel.receiveAsFlow()

    private val email = savedStateHandle.get<String>("email")
        ?: throw IllegalStateException("No email passed to register success screen")
    private val _state = MutableStateFlow(RegisterSuccessState(
        registeredEmail = email
    ))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterSuccessState()
        )

    fun onAction(action: RegisterSuccessAction) {
        when (action) {
            is RegisterSuccessAction.OnResendVerificationEmailClick -> resendVerification()
            else -> Unit
        }
    }

    private fun resendVerification() {
        if(state.value.isResendingVerificationEmail) {
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(
                isResendingVerificationEmail = true
            ) }

            authService
                .resendVerificationEmail(email)
                .onSuccess {
                    _state.update { it.copy(
                        isResendingVerificationEmail = false
                    ) }
                    eventChannel.send(RegisterSuccessEvent.ResendVerificationEmailSuccess)
                }
                .onFailure { error ->
                    _state.update { it.copy(
                        isResendingVerificationEmail = false,
                        resendVerificationError = error.toUiText()
                    ) }
                }
        }
    }

}