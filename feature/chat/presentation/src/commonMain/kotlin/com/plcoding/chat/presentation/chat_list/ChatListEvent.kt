package com.plcoding.chat.presentation.chat_list

import com.plcoding.core.presentation.util.UiText

sealed interface ChatListEvent {
    data object OnLogoutSuccess: ChatListEvent
    data class OnLogoutError(val error: UiText): ChatListEvent
}