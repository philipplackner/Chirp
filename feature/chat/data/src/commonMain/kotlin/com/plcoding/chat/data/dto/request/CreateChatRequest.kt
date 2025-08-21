package com.plcoding.chat.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val otherUserIds: List<String>
)
