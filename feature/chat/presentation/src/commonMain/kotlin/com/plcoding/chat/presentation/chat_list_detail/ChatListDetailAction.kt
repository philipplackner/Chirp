package com.plcoding.chat.presentation.chat_list_detail

sealed interface ChatListDetailAction {
    data class OnChatClick(val chatId: String?): ChatListDetailAction
    data object OnProfileSettingsClick: ChatListDetailAction
    data object OnCreateChatClick: ChatListDetailAction
    data object OnManageChatClick: ChatListDetailAction
    data object OnDismissCurrentDialog: ChatListDetailAction
}