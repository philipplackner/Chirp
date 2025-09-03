package com.plcoding.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.chat.data.participant.KtorChatParticipantService
import com.plcoding.chat.data.chat.KtorChatService
import com.plcoding.chat.data.chat.OfflineFirstChatRepository
import com.plcoding.chat.data.chat.WebSocketChatConnectionClient
import com.plcoding.chat.data.lifecycle.AppLifecycleObserver
import com.plcoding.chat.data.message.KtorChatMessageService
import com.plcoding.chat.data.message.OfflineFirstMessageRepository
import com.plcoding.chat.data.network.ConnectionErrorHandler
import com.plcoding.chat.data.network.ConnectionRetryHandler
import com.plcoding.chat.data.network.KtorWebSocketConnector
import com.plcoding.chat.data.notification.KtorDeviceTokenService
import com.plcoding.chat.data.participant.OfflineFirstChatParticipantRepository
import com.plcoding.chat.database.DatabaseFactory
import com.plcoding.chat.domain.chat.ChatConnectionClient
import com.plcoding.chat.domain.participant.ChatParticipantService
import com.plcoding.chat.domain.chat.ChatRepository
import com.plcoding.chat.domain.chat.ChatService
import com.plcoding.chat.domain.message.ChatMessageService
import com.plcoding.chat.domain.message.MessageRepository
import com.plcoding.chat.domain.notification.DeviceTokenService
import com.plcoding.chat.domain.participant.ChatParticipantRepository
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module

val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    singleOf(::OfflineFirstChatRepository) bind ChatRepository::class
    singleOf(::OfflineFirstMessageRepository) bind MessageRepository::class
    singleOf(::WebSocketChatConnectionClient) bind ChatConnectionClient::class
    singleOf(::ConnectionRetryHandler)
    singleOf(::KtorWebSocketConnector)
    singleOf(::KtorChatMessageService) bind ChatMessageService::class
    singleOf(::KtorDeviceTokenService) bind DeviceTokenService::class
    singleOf(::OfflineFirstChatParticipantRepository) bind ChatParticipantRepository::class
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}