package com.plcoding.chirp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.TrayState
import com.plcoding.core.domain.preferences.ThemePreference
import org.jetbrains.compose.resources.painterResource
import chirp.composeapp.generated.resources.Res
import chirp.composeapp.generated.resources.app_theme
import chirp.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.stringResource

@Composable
fun ApplicationScope.ChirpTrayMenu(
    state: TrayState,
    themePreferenceFromAppSettings: ThemePreference,
    onThemePreferenceClick: (ThemePreference) -> Unit
) {
    Tray(
        icon = painterResource(Res.drawable.logo),
        state = state,
    ) {
        Menu(
            text = stringResource(Res.string.app_theme)
        ) {
            ThemePreference.entries.forEach { themePreference ->
                CheckboxItem(
                    text = themePreference.name.lowercase().replaceFirstChar { it.titlecase() },
                    onCheckedChange = {
                        onThemePreferenceClick(themePreference)
                    },
                    checked = themePreferenceFromAppSettings == themePreference
                )
            }
        }
    }
}