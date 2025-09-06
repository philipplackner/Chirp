package com.plcoding.core.data.util

actual object PlatformUtils {
    actual fun getOSName(): String {
        return System.getProperty("os.name")
    }
}