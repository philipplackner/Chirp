package com.plcoding.chat.data.di

import com.plcoding.chat.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory() }
}