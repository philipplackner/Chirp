package com.plcoding.chat.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class ConnectivityObserver {
    actual val isConnected: Flow<Boolean>
        get() = flowOf(true)
}