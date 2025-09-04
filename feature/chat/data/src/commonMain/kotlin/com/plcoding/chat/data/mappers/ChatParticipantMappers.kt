package com.plcoding.chat.data.mappers

import com.plcoding.chat.data.dto.ChatParticipantDto
import com.plcoding.chat.database.entities.ChatParticipantEntity
import com.plcoding.chat.domain.models.ChatParticipant
import com.plcoding.core.domain.auth.User

fun ChatParticipantDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}

fun ChatParticipantEntity.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}

fun ChatParticipant.toEntity(): ChatParticipantEntity {
    return ChatParticipantEntity(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}