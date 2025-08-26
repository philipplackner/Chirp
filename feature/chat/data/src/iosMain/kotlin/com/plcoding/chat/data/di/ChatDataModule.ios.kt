package com.plcoding.chat.data.di

import com.plcoding.chat.data.lifecycle.AppLifecycleObserver
import com.plcoding.chat.data.network.ConnectivityObserver
import com.plcoding.chat.database.DatabaseFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory() }
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
}