package com.plcoding.chirp.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import chirp.composeapp.generated.resources.Res
import chirp.composeapp.generated.resources.file
import chirp.composeapp.generated.resources.logo
import chirp.composeapp.generated.resources.new_window
import chirp.core.designsystem.generated.resources.logo_chirp
import com.plcoding.chirp.App
import com.plcoding.chirp.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChirpWindow(
    appTheme: AppTheme,
    onCloseRequest: () -> Unit,
    onAddWindowClick: () -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    onDeepLinkListenerSetup: () -> Unit,
) {
    val windowState = rememberWindowState(
        width = 1200.dp,
        height = 800.dp
    )
    Window(
        onCloseRequest = onCloseRequest,
        state = windowState,
        title = "Chirp",
        icon = painterResource(Res.drawable.logo)
    ) {
        FocusObserver(
            onFocusChanged = onFocusChanged
        )

        MenuBar {
            Menu(
                text = stringResource(Res.string.file),
                mnemonic = 'F'
            ) {
                Item(
                    text = stringResource(Res.string.new_window),
                    mnemonic = 'N',
                    shortcut = KeyShortcut(
                        key = Key.N,
                        ctrl = true,
                        shift = true
                    ),
                    onClick = onAddWindowClick
                )
            }
        }

        App(
            isDarkTheme = appTheme == AppTheme.DARK,
            onDeepLinkListenerSetup = onDeepLinkListenerSetup
        )
    }
}