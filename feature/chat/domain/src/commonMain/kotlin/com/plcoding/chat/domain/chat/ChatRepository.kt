package com.plcoding.chat.domain.chat

import com.plcoding.chat.domain.models.Chat
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
}