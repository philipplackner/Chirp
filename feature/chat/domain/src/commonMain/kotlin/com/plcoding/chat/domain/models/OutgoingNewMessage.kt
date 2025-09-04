package com.plcoding.chat.domain.models

data class OutgoingNewMessage(
    val chatId: String,
    val messageId: String,
    val content: String
)
