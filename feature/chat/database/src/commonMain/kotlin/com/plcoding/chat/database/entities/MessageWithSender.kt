package com.plcoding.chat.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithSender(
    @Embedded
    val message: ChatMessageEntity,
    @Relation(
        parentColumn = "senderId",
        entityColumn = "userId"
    )
    val sender: ChatParticipantEntity
)
