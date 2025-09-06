package com.plcoding.chat.data.network

import com.plcoding.chat.domain.models.ConnectionState

actual class ConnectionErrorHandler {
    actual fun getConnectionStateForError(cause: Throwable): ConnectionState {
        return ConnectionState.ERROR_NETWORK
    }

    actual fun transformException(exception: Throwable): Throwable {
        return exception
    }

    actual fun isRetriableError(cause: Throwable): Boolean {
        return true
    }
}