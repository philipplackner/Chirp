package com.plcoding.chirp

import androidx.compose.runtime.Composable
import com.plcoding.auth.presentation.register.RegisterRoot
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterRoot()
    }
}