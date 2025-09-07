package com.plcoding.chat.presentation.profile.mediapicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.select_a_profile_picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.stringResource
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter
import java.nio.file.Files
import javax.swing.SwingUtilities
import kotlin.coroutines.resume

@Composable
actual fun rememberImagePickerLauncher(onResult: (PickedImageData) -> Unit): ImagePickerLauncher {
    val scope = rememberCoroutineScope()
    val dialogTitle = stringResource(Res.string.select_a_profile_picture)
    return remember {
        ImagePickerLauncher(
            onLaunch = {
                scope.launch {
                    pickImage(dialogTitle)?.let { data ->
                        onResult(data)
                    }
                }
            }
        )
    }
}

internal val allowedImageExtensions = listOf(
    "png",
    "jpg",
    "jpeg",
    "webp",
)

fun getMimeTypeFromFileName(fileName: String): String? {
    val extension = fileName.substringAfterLast(".", "").lowercase()
    return when(extension) {
        "png" -> "image/png"
        "jpg", "jpeg" -> "image/jpeg"
        "webp" -> "image/webp"
        else -> null
    }
}

private suspend fun pickImage(fileDialogTitle: String): PickedImageData? {
    val file = suspendCancellableCoroutine<File?> { continuation ->
        var fileDialog: FileDialog? = null

        continuation.invokeOnCancellation {
            SwingUtilities.invokeLater {
                fileDialog?.dispose()
            }
        }

        SwingUtilities.invokeLater {
            try {
                fileDialog = FileDialog(Frame(), fileDialogTitle, FileDialog.LOAD)
                fileDialog.filenameFilter = FilenameFilter { _, name ->
                    allowedImageExtensions.any {
                        name.endsWith(it)
                    }
                }
                fileDialog.isVisible = true

                val file = File(fileDialog.directory, fileDialog.file)

                continuation.resume(file)
            } catch (_: Exception) {
                continuation.resume(null)
            }
        }
    }

    return withContext(Dispatchers.IO) {
        if(file != null) {
            try {
                val mimeType = getMimeTypeFromFileName(file.name)
                val bytes = Files.readAllBytes(file.toPath())
                PickedImageData(
                    bytes = bytes,
                    mimeType = mimeType
                )
            } catch(_: Exception) {
                coroutineContext.ensureActive()
                null
            }
        } else {
            null
        }
    }
}