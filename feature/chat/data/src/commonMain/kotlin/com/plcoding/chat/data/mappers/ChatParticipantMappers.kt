package com.plcoding.chat.data.mappers

import com.plcoding.chat.data.dto.ChatParticipantDto
import com.plcoding.chat.domain.models.ChatParticipant

fun ChatParticipantDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}