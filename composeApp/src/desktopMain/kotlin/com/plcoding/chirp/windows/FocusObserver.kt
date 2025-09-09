package com.plcoding.chirp.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.FrameWindowScope
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener

@Composable
fun FrameWindowScope.FocusObserver(
    onFocusChanged: (Boolean) -> Unit
) {
    DisposableEffect(Unit) {
        val focusListener = object : WindowFocusListener {
            override fun windowGainedFocus(p0: WindowEvent?) {
                onFocusChanged(true)
            }

            override fun windowLostFocus(p0: WindowEvent?) {
                onFocusChanged(false)
            }
        }

        window.addWindowFocusListener(focusListener)

        onDispose {
            window.removeWindowFocusListener(focusListener)
        }
    }
}