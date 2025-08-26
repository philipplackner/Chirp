package com.plcoding.chat.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.chat.database.dao.ChatDao
import com.plcoding.chat.database.dao.ChatMessageDao
import com.plcoding.chat.database.dao.ChatParticipantDao
import com.plcoding.chat.database.dao.ChatParticipantsCrossRefDao
import com.plcoding.chat.database.entities.ChatEntity
import com.plcoding.chat.database.entities.ChatMessageEntity
import com.plcoding.chat.database.entities.ChatParticipantCrossRef
import com.plcoding.chat.database.entities.ChatParticipantEntity
import com.plcoding.chat.database.view.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatParticipantEntity::class,
        ChatMessageEntity::class,
        ChatParticipantCrossRef::class,
    ],
    views = [
        LastMessageView::class
    ],
    version = 1,
)
@ConstructedBy(ChirpChatDatabaseConstructor::class)
abstract class ChirpChatDatabase: RoomDatabase() {
    abstract val chatDao: ChatDao
    abstract val chatParticipantDao: ChatParticipantDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val chatParticipantsCrossRefDao: ChatParticipantsCrossRefDao

    companion object {
        const val DB_NAME = "chirp.db"
    }
}