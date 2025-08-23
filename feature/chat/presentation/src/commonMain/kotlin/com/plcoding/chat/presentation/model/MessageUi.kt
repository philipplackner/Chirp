package com.plcoding.chat.presentation.model

import com.plcoding.chat.domain.models.ChatMessageDeliveryStatus
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi
import com.plcoding.core.presentation.util.UiText

sealed interface MessageUi {
    data class LocalUserMessage(
        val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val isMenuOpen: Boolean,
        val formattedSentTime: UiText
    ): MessageUi

    data class OtherUserMessage(
        val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUi
    ): MessageUi

    data class DateSeparator(
        val id: String,
        val date: UiText,
    ): MessageUi
}