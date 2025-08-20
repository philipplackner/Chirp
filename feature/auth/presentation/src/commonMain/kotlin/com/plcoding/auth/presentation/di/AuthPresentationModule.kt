package com.plcoding.auth.presentation.di

import com.plcoding.auth.presentation.email_verification.EmailVerificationViewModel
import com.plcoding.auth.presentation.forgot_password.ForgotPasswordViewModel
import com.plcoding.auth.presentation.login.LoginViewModel
import com.plcoding.auth.presentation.register.RegisterViewModel
import com.plcoding.auth.presentation.register_success.RegisterSuccessViewModel
import com.plcoding.auth.presentation.reset_password.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
}