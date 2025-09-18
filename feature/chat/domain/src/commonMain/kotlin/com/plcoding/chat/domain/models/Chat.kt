package com.plcoding.chat.domain.models

import kotlin.time.Instant

data class Chat(
    val id: String,
    val participants: List<ChatParticipant>,
    val lastActivityAt: Instant,
    val lastMessage: ChatMessage?,
    val lastMessageSenderUsername: String? = null
)
