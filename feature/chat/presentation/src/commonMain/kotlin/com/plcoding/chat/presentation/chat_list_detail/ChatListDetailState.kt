package com.plcoding.chat.presentation.chat_list_detail

data class ChatListDetailState(
    val selectedChatId: String? = null,
    val dialogState: DialogState = DialogState.Hidden
)

sealed interface DialogState {
    data object Hidden: DialogState
    data object CreateChat: DialogState
    data object Profile: DialogState
    data class ManageChat(val chatId: String): DialogState
}
