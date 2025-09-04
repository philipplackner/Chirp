package com.plcoding.chat.presentation.create_chat

import com.plcoding.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(val chat: Chat): CreateChatEvent
}