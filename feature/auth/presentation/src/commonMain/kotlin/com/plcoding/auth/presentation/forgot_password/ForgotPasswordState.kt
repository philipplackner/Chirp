package com.plcoding.auth.presentation.forgot_password

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.util.UiText

data class ForgotPasswordState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val canSubmit: Boolean = false,
    val isLoading: Boolean = false,
    val errorText: UiText? = null,
    val isEmailSentSuccessfully: Boolean = false
)