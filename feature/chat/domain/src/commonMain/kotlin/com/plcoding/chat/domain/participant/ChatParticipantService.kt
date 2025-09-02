package com.plcoding.chat.domain.participant

import com.plcoding.chat.domain.models.ChatParticipant
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(
        query: String
    ): Result<ChatParticipant, DataError.Remote>

    suspend fun getLocalParticipant(): Result<ChatParticipant, DataError.Remote>
}