package com.plcoding.chirp

import com.plcoding.chirp.windows.WindowState

data class ApplicationState(
    val windows: List<WindowState> = listOf(WindowState())
)
