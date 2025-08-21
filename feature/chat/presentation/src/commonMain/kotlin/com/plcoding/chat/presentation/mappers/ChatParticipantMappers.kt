package com.plcoding.chat.presentation.mappers

import com.plcoding.chat.domain.models.ChatParticipant
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}