package com.plcoding.chirp.windows

import java.util.UUID

data class WindowState(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "Chirp",
    val isFocused: Boolean = false
)
