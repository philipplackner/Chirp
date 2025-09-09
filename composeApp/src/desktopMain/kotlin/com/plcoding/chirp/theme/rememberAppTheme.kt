package com.plcoding.chirp.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jthemedetecor.OsThemeDetector
import com.plcoding.core.domain.preferences.ThemePreference
import java.util.function.Consumer

enum class AppTheme {
    LIGHT, DARK
}

@Composable
fun rememberAppTheme(
    themePreferenceFromAppSettings: ThemePreference
): AppTheme {
    var isSystemThemeDark by remember {
        if(OsThemeDetector.isSupported()) {
            mutableStateOf(OsThemeDetector.getDetector().isDark)
        } else {
            val isSettingsPreferenceDark = themePreferenceFromAppSettings == ThemePreference.DARK
            mutableStateOf(isSettingsPreferenceDark)
        }
    }

    DisposableEffect(Unit) {
        var listener: Consumer<Boolean>? = null
        if(OsThemeDetector.isSupported()) {
            listener = Consumer<Boolean> { dark -> isSystemThemeDark = dark }
            OsThemeDetector.getDetector().registerListener(listener)
        }

        onDispose {
            OsThemeDetector.getDetector().removeListener(listener)
        }
    }

    val isDarkTheme = when(themePreferenceFromAppSettings) {
        ThemePreference.LIGHT -> false
        ThemePreference.DARK -> true
        ThemePreference.SYSTEM -> isSystemThemeDark
    }

    return if(isDarkTheme) AppTheme.DARK else AppTheme.LIGHT
}