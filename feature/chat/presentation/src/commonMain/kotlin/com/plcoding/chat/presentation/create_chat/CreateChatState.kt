package com.plcoding.chat.presentation.create_chat

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi
import com.plcoding.core.presentation.util.UiText

data class CreateChatState(
    val queryTextState: TextFieldState = TextFieldState(),
    val selectedChatParticipants: List<ChatParticipantUi> = emptyList(),
    val isSearching: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipantUi? = null,
    val searchError: UiText? = null,
    val isCreatingChat: Boolean = false,
    val createChatError: UiText? = null
)