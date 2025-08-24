package com.plcoding.chat.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    @PrimaryKey
    val chatId: String,
    val lastActivityAt: Long
)
