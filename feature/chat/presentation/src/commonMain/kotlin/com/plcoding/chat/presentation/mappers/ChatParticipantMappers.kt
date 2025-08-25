package com.plcoding.chat.presentation.mappers

import com.plcoding.chat.domain.models.ChatParticipant
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi
import com.plcoding.core.domain.auth.User

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}

fun User.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = id,
        username = username,
        initials = username.take(2).uppercase(),
        imageUrl = profilePictureUrl
    )
}