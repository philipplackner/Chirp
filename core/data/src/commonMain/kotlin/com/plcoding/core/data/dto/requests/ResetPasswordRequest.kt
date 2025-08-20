package com.plcoding.core.data.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val newPassword: String,
    val token: String
)
