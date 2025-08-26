package com.plcoding.chat.domain.error

import com.plcoding.core.domain.util.Error

enum class ConnectionError: Error {
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED
}