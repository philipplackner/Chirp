package com.plcoding.core.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberPermissionController(): PermissionController {
    return remember { PermissionController() }
}