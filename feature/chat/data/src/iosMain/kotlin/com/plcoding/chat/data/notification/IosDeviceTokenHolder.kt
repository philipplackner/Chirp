package com.plcoding.chat.data.notification

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object IosDeviceTokenHolder {

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    fun updateToken(token: String?) {
        _token.value = token
    }
}