package com.plcoding.chat.data.network

import kotlinx.coroutines.delay
import kotlin.math.pow

class ConnectionRetryHandler(
    private val connectionErrorHandler: ConnectionErrorHandler
) {
    private var shouldSkipBackoff = false

    fun shouldRetry(cause: Throwable, attempt: Long): Boolean {
        return connectionErrorHandler.isRetriableError(cause)
    }

    suspend fun applyRetryDelay(attempt: Long) {
        if(!shouldSkipBackoff) {
            val delay = createBackoffDelay(attempt)
            delay(delay)
        } else {
            shouldSkipBackoff = false
        }
    }

    fun resetDelay() {
        shouldSkipBackoff = true
    }

    private fun createBackoffDelay(attempt: Long): Long {
        val delayTime = (2f.pow(attempt.toInt()) * 2000L).toLong()
        val maxDelay = 30_000L
        return minOf(delayTime, maxDelay)
    }
}