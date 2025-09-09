package com.plcoding.chirp.deeplink

import com.plcoding.chirp.navigation.ExternalUriHandler
import java.awt.Desktop
import javax.swing.SwingUtilities

object DesktopDeepLinkHandler {

    val supportedUriPatterns = listOf(
        Regex("^chirp://.*"),
        Regex("^https?://chirp\\.pl-coding\\.com/.*"),
    )

    private var isInitialized = false

    fun setup() {
        if(isInitialized) {
            return
        }

        if(!Desktop.isDesktopSupported()) {
            return
        }

        try {
            val desktop = Desktop.getDesktop()
            if(desktop.isSupported(Desktop.Action.APP_OPEN_URI)) {
                desktop.setOpenURIHandler { event ->
                    val uri = event.uri.toString()
                    SwingUtilities.invokeLater {
                        processUri(uri)
                    }
                }
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    private fun processUri(uri: String) {
        val cleanUri = uri.trim('"', ' ')

        if(!isValidUri(uri)) {
            return
        }

        ExternalUriHandler.onNewUri(cleanUri)
    }

    private fun isValidUri(uri: String): Boolean {
        return supportedUriPatterns.any { it.matches(uri) }
    }
}