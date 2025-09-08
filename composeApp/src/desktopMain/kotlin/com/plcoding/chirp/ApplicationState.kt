package com.plcoding.chirp

import androidx.compose.ui.window.TrayState
import com.plcoding.chirp.windows.WindowState
import com.plcoding.core.domain.preferences.ThemePreference

data class ApplicationState(
    val windows: List<WindowState> = listOf(WindowState()),
    val themePreference: ThemePreference = ThemePreference.SYSTEM,
    val trayState: TrayState = TrayState()
)
