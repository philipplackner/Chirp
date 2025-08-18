package com.plcoding.chirp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.plcoding.auth.presentation.register.RegisterRoot
import com.plcoding.auth.presentation.register_success.RegisterSuccessRoot
import com.plcoding.chirp.navigation.DeepLinkListener
import com.plcoding.chirp.navigation.NavigationRoot
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    DeepLinkListener(navController)

    ChirpTheme {
        NavigationRoot(navController)
    }
}