package com.plcoding.chat.data.network

import com.plcoding.core.domain.logging.ChirpLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.Socket
import kotlin.coroutines.coroutineContext

actual class ConnectivityObserver(
    private val chirpLogger: ChirpLogger
) {
    actual val isConnected = flow {
        while(true) {
            val connected = isConnected()
            chirpLogger.info("Connectivity state on Desktop: $connected")
            emit(connected)
            delay(5000L)
        }
    }

    private val connectivityTargets = listOf(
        InetSocketAddress("8.8.8.8", 53),
        InetSocketAddress("1.1.1.1", 53),
        InetSocketAddress("208.67.222.222", 53),
    )

    private suspend fun isConnected(): Boolean {
        val hasInterface = try {
            NetworkInterface.getNetworkInterfaces()
                .asSequence()
                .any { networkInterface ->
                    !networkInterface.isLoopback &&
                            networkInterface.isUp &&
                            networkInterface.inetAddresses.hasMoreElements()
                }
        } catch(_: Exception) {
            coroutineContext.ensureActive()
            false
        }

        if(!hasInterface) {
            return false
        }

        return withContext(Dispatchers.IO) {
            connectivityTargets.any { target ->
                try {
                    Socket().use {
                        it.soTimeout = 3000
                        it.connect(target)
                        true
                    }
                } catch(_: Exception) {
                    kotlin.coroutines.coroutineContext.ensureActive()
                    false
                }
            }
        }
    }
}