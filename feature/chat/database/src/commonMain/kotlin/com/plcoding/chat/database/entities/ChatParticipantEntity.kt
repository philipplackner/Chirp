package com.plcoding.chat.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatParticipantEntity(
    @PrimaryKey
    val userId: String,
    val username: String,
    val profilePictureUrl: String?
)
