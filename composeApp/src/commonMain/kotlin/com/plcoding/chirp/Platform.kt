package com.plcoding.chirp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform