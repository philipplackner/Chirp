package com.plcoding.auth.presentation.login

sealed interface LoginEvent {
    data object Success: LoginEvent
}