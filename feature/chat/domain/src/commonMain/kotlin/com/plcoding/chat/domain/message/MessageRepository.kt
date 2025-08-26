package com.plcoding.chat.domain.message

import com.plcoding.chat.domain.models.ChatMessageDeliveryStatus
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>
}