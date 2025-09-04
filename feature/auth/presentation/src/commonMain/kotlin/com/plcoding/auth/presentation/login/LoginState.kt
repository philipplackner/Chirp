package com.plcoding.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.util.UiText

data class LoginState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
    val error: UiText? = null
)