package com.plcoding.core.presentation.permissions

actual class PermissionController {
    actual suspend fun requestPermission(permission: Permission): PermissionState {
        return PermissionState.GRANTED
    }
}