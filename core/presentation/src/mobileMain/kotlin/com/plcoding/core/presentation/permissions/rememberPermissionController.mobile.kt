package com.plcoding.core.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

@Composable
actual fun rememberPermissionController(): PermissionController {
    val factory = rememberPermissionsControllerFactory()
    val mokoController = remember {
        factory.createPermissionsController()
    }

    BindEffect(mokoController)

    return remember {
        PermissionController(mokoController)
    }
}