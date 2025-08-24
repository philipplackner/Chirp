package com.plcoding.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.chat.data.chat.KtorChatParticipantService
import com.plcoding.chat.data.chat.KtorChatService
import com.plcoding.chat.database.DatabaseFactory
import com.plcoding.chat.domain.chat.ChatParticipantService
import com.plcoding.chat.domain.chat.ChatService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module

val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}