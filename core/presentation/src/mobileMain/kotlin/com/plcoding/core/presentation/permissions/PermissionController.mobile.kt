package com.plcoding.core.presentation.permissions

import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION

actual class PermissionController(
    private val mokoPermissionsController: PermissionsController
) {
    actual suspend fun requestPermission(permission: Permission): PermissionState {
        return try {
            mokoPermissionsController.providePermission(permission.toMokoPermission())
            PermissionState.GRANTED
        } catch (_: DeniedAlwaysException) {
            PermissionState.PERMANENTLY_DENIED
        } catch(_: DeniedException) {
            PermissionState.DENIED
        } catch(_: RequestCanceledException) {
            PermissionState.DENIED
        }
    }
}

fun Permission.toMokoPermission(): dev.icerock.moko.permissions.Permission {
    return when(this) {
        Permission.NOTIFICATIONS -> dev.icerock.moko.permissions.Permission.REMOTE_NOTIFICATION
    }
}