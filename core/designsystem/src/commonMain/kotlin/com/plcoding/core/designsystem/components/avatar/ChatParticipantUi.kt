package com.plcoding.core.designsystem.components.avatar

data class ChatParticipantUi(
    val id: String,
    val username: String,
    val initials: String,
    val imageUrl: String? = null
)
