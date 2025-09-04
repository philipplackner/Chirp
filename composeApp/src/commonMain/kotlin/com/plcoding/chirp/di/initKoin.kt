package com.plcoding.chirp.di

import com.plcoding.auth.presentation.di.authPresentationModule
import com.plcoding.chat.data.di.chatDataModule
import com.plcoding.chat.presentation.di.chatPresentationModule
import com.plcoding.core.data.di.coreDataModule
import com.plcoding.core.presentation.di.corePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appModule,
            chatPresentationModule,
            corePresentationModule,
            chatDataModule
        )
    }
}