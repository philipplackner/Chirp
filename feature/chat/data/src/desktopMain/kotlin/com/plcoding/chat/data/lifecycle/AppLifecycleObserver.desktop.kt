package com.plcoding.chat.data.lifecycle

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class AppLifecycleObserver {
    // Don't disconnect from socket on desktop
    actual val isInForeground: Flow<Boolean>
        get() = flowOf(true)
}