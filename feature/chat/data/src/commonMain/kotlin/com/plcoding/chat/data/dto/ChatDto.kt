package com.plcoding.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,
    val participants: List<ChatParticipantDto>,
    val lastActivityAt: String,
    val lastMessage: ChatMessageDto?
)
