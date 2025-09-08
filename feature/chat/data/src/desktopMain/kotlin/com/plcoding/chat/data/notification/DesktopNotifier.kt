package com.plcoding.chat.data.notification

import com.plcoding.chat.domain.chat.ChatConnectionClient
import com.plcoding.chat.domain.chat.ChatRepository
import com.plcoding.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class DesktopNotifier(
    private val chatConnectionClient: ChatConnectionClient,
    private val sessionStorage: SessionStorage,
    private val chatRepository: ChatRepository
) {
    data class NotificationPayload(
        val title: String,
        val message: String
    )

    fun observeNewNotifications(): Flow<NotificationPayload> {
        return combine(
            chatConnectionClient.chatMessages,
            sessionStorage.observeAuthInfo()
        ) { chatMessage, authInfo ->
            val currentUserId = authInfo?.user?.id
            if(chatMessage.senderId != currentUserId) {
                (chatMessage to currentUserId)
            } else null
        }
            .filterNotNull()
            .distinctUntilChangedBy { (message, _) -> message.id }
            .map { (message, currentUserId) ->
                val chatInfo = chatRepository.getChatInfoById(message.chatId).firstOrNull()

                val senderName = chatInfo?.chat?.participants?.find {
                    it.userId == message.senderId
                }?.username

                val notificationTitle = chatInfo?.chat?.participants?.let { participants ->
                    participants
                        .filter { it.userId != currentUserId }
                        .sortedBy { it.username }
                        .joinToString(", ") { it.username }
                }

                NotificationPayload(
                    title = notificationTitle ?: "Unknown",
                    message = "$senderName: ${message.content}"
                )
            }
    }
}