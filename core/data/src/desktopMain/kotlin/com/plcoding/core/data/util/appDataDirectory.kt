package com.plcoding.core.data.util

import java.io.File

val appDataDirectory: File
    get() {
        val userHome = System.getProperty("user.home")
        return when(currentOs) {
            DesktopOs.WINDOWS -> File(System.getenv("APPDATA"), "Chirp")
            DesktopOs.MACOS -> File(userHome, "Library/Application Support/Chirp")
            DesktopOs.LINUX -> File(userHome, ".local/share/Chirp")
        }
    }