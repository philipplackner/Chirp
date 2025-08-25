package com.plcoding.chat.data.chat

import com.plcoding.chat.data.mappers.toDomain
import com.plcoding.chat.data.mappers.toEntity
import com.plcoding.chat.data.mappers.toLastMessageView
import com.plcoding.chat.database.ChirpChatDatabase
import com.plcoding.chat.database.entities.ChatWithParticipants
import com.plcoding.chat.domain.chat.ChatRepository
import com.plcoding.chat.domain.chat.ChatService
import com.plcoding.chat.domain.models.Chat
import com.plcoding.chat.domain.models.ChatInfo
import com.plcoding.core.domain.util.DataError
import com.plcoding.core.domain.util.EmptyResult
import com.plcoding.core.domain.util.Result
import com.plcoding.core.domain.util.asEmptyResult
import com.plcoding.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase
): ChatRepository {

    override fun getChats(): Flow<List<Chat>> {
        return db.chatDao.getChatsWithActiveParticipants()
            .map { chatWithParticipantsList ->
                chatWithParticipantsList.map { it.toDomain() }
            }
    }

    override fun getChatInfoById(chatId: String): Flow<ChatInfo> {
        return db.chatDao.getChatInfoById(chatId)
            .filterNotNull()
            .map { it.toDomain() }
    }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> {
        return chatService
            .getChats()
            .onSuccess { chats ->
                val chatsWithParticipants = chats.map { chat ->
                    ChatWithParticipants(
                        chat = chat.toEntity(),
                        participants = chat.participants.map { it.toEntity() },
                        lastMessage = chat.lastMessage?.toLastMessageView()
                    )
                }

                db.chatDao.upsertChatsWithParticipantsAndCrossRefs(
                    chats = chatsWithParticipants,
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                    messageDao = db.chatMessageDao
                )
            }
    }

    override suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote> {
        return chatService
            .getChatById(chatId)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participants.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao
                )
            }
            .asEmptyResult()
    }

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return chatService
            .createChat(otherUserIds)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participants.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao
                )
            }
    }
}