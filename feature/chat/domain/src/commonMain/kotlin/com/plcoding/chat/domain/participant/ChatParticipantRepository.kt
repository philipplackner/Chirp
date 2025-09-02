package com.plcoding.chat.domain.participant

import com.plcoding.chat.domain.models.ChatParticipant
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult
import com.plcoding.core.domain.util.Result

interface ChatParticipantRepository {
    suspend fun fetchLocalParticipant(): Result<ChatParticipant, DataError>
    suspend fun uploadProfilePicture(
        imageBytes: ByteArray,
        mimeType: String
    ): EmptyResult<DataError.Remote>

    suspend fun deleteProfilePicture(): EmptyResult<DataError.Remote>
}