package com.plcoding.chat.data.network

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfiable
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_queue_create

actual class ConnectivityObserver {
    actual val isConnected: Flow<Boolean> = callbackFlow {
        val pathMonitor = nw_path_monitor_create()

        val queue = dispatch_queue_create(
            NW_PATH_MONITOR_LABEL,
            null
        )

        nw_path_monitor_set_update_handler(pathMonitor) { path ->
            if(path != null) {
                val status = nw_path_get_status(path)

                val isConnected = when(status) {
                    nw_path_status_satisfied -> true
                    nw_path_status_satisfiable -> true
                    else -> false
                }

                trySend(isConnected)
            }
        }

        nw_path_monitor_set_queue(pathMonitor, queue)
        nw_path_monitor_start(pathMonitor)

        awaitClose {
            nw_path_monitor_cancel(pathMonitor)
        }
    }

    companion object {
        private const val NW_PATH_MONITOR_LABEL = "com.plcoding.chat.data.network.ConnectivityObserver"
    }
}