package com.plcoding.chat.presentation.util

import com.plcoding.chat.presentation.Res
import com.plcoding.chat.presentation.network_error
import com.plcoding.chat.presentation.offline
import com.plcoding.chat.presentation.online
import com.plcoding.chat.presentation.reconnecting
import com.plcoding.chat.presentation.unknown_error
import com.plcoding.chat.domain.models.ConnectionState
import com.plcoding.core.presentation.util.UiText

fun ConnectionState.toUiText(): UiText {
    val resource = when(this) {
        ConnectionState.DISCONNECTED -> Res.string.offline
        ConnectionState.CONNECTING -> Res.string.reconnecting
        ConnectionState.CONNECTED -> Res.string.online
        ConnectionState.ERROR_NETWORK -> Res.string.network_error
        ConnectionState.ERROR_UNKNOWN -> Res.string.unknown_error
    }
    return UiText.Resource(resource)
}