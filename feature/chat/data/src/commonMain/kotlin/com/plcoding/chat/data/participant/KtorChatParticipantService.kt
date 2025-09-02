package com.plcoding.chat.data.participant

import com.plcoding.chat.data.dto.ChatParticipantDto
import com.plcoding.chat.data.mappers.toDomain
import com.plcoding.chat.domain.participant.ChatParticipantService
import com.plcoding.chat.domain.models.ChatParticipant
import com.plcoding.core.data.networking.get
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.Result
import com.plcoding.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatParticipantService(
    private val httpClient: HttpClient
): ChatParticipantService {

    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants",
            queryParams = mapOf(
                "query" to query
            )
        ).map { it.toDomain() }
    }

    override suspend fun getLocalParticipant(): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants"
        ).map { it.toDomain() }
    }
}