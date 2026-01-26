package com.plcoding.chat.presentation.mappers

import com.plcoding.chat.domain.models.Chat
import com.plcoding.chat.presentation.model.ChatUi

fun Chat.toUi(localParticipantId: String): ChatUi {
    val (local, other) = participants.partition { it.userId == localParticipantId }
    return ChatUi(
        id = id,
        localParticipant = local.firstOrNull()?.toUi() ?: throw IllegalStateException("Local participant not found in chat"),
        otherParticipants = other.map { it.toUi() },
        lastMessage = lastMessage,
        lastMessageSenderUsername = lastMessageSenderUsername
    )
}