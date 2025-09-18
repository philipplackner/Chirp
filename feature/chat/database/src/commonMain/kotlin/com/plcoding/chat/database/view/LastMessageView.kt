package com.plcoding.chat.database.view

import androidx.room.DatabaseView

@DatabaseView(
    viewName = "last_message_view_per_chat",
    value = """
        SELECT m1.*, p.username AS senderUsername
        FROM chatmessageentity m1
        JOIN (
            SELECT chatId, MAX(timestamp) AS max_timestamp
            FROM chatmessageentity
            GROUP BY chatId
        ) m2 ON m1.chatId = m2.chatId AND m1.timestamp = m2.max_timestamp
        LEFT JOIN chatparticipantentity p ON m1.senderId = p.userId
    """
)
data class LastMessageView(
    val messageId: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val deliveryStatus: String,
    val senderUsername: String?
)
