package com.plcoding.chat.presentation.profile

sealed interface ProfileAction {
    data object OnDismiss: ProfileAction
    data object OnUploadPictureClick: ProfileAction
    class OnPictureSelected(val bytes: ByteArray, val mimeType: String?): ProfileAction
    data object OnDeletePictureClick: ProfileAction
    data object OnConfirmDeleteClick: ProfileAction
    data object OnDismissDeleteConfirmationDialogClick: ProfileAction
    data object OnToggleCurrentPasswordVisibility: ProfileAction
    data object OnToggleNewPasswordVisibility: ProfileAction
    data object OnChangePasswordClick: ProfileAction
}