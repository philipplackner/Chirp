package com.plcoding.chat.presentation.util

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.network_error
import chirp.feature.chat.presentation.generated.resources.offline
import chirp.feature.chat.presentation.generated.resources.online
import chirp.feature.chat.presentation.generated.resources.reconnecting
import chirp.feature.chat.presentation.generated.resources.unknown_error
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