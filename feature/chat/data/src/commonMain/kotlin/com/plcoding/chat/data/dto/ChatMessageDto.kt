package com.plcoding.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    val id: String,
    val chatId: String,
    val content: String,
    val createdAt: String,
    val senderId: String
)
