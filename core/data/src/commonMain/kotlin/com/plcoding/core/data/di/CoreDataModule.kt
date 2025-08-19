package com.plcoding.core.data.di

import com.plcoding.core.data.auth.DataStoreSessionStorage
import com.plcoding.core.data.auth.KtorAuthService
import com.plcoding.core.data.logging.KermitLogger
import com.plcoding.core.data.networking.HttpClientFactory
import com.plcoding.core.domain.auth.AuthService
import com.plcoding.core.domain.auth.SessionStorage
import com.plcoding.core.domain.logging.ChirpLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    single<ChirpLogger> { KermitLogger }
    single {
        HttpClientFactory(get(), get()).create(get())
    }
    singleOf(::KtorAuthService) bind AuthService::class
    singleOf(::DataStoreSessionStorage) bind SessionStorage::class
}