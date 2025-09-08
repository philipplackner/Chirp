package com.plcoding.chat.data.di

import com.plcoding.chat.data.lifecycle.AppLifecycleObserver
import com.plcoding.chat.data.network.ConnectionErrorHandler
import com.plcoding.chat.data.network.ConnectivityObserver
import com.plcoding.chat.data.notification.DesktopNotifier
import com.plcoding.chat.data.notification.FirebasePushNotificationService
import com.plcoding.chat.database.DatabaseFactory
import com.plcoding.chat.domain.notification.PushNotificationService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataModule = module {
    singleOf(::DatabaseFactory)
    singleOf(::ConnectionErrorHandler)
    singleOf(::ConnectivityObserver)
    singleOf(::AppLifecycleObserver)
    singleOf(::DesktopNotifier)
    singleOf(::FirebasePushNotificationService) bind PushNotificationService::class
}