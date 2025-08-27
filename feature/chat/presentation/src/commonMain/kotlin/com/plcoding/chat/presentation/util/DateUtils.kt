package com.plcoding.chat.presentation.util

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.today
import chirp.feature.chat.presentation.generated.resources.yesterday
import com.plcoding.core.presentation.util.UiText
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

object DateUtils {

    fun formatMessageTime(instant: Instant, clock: Clock = Clock.System): UiText {
        val timeZone = TimeZone.currentSystemDefault()
        val messageDateTime = instant.toLocalDateTime(timeZone)
        val todayDate = clock.now().toLocalDateTime(timeZone).date
        val yesterdayDate = todayDate.minus(1, DateTimeUnit.DAY)

        val formattedDateTime = messageDateTime.format(
            LocalDateTime.Format {
                day()
                char('/')
                monthNumber()
                char('/')
                year()
                char(' ')
                amPmHour()
                char(':')
                minute()
                amPmMarker("am", "pm")
            }
        )

        return when(messageDateTime.date) {
            todayDate -> UiText.Resource(Res.string.today)
            yesterdayDate -> UiText.Resource(Res.string.yesterday)
            else -> UiText.DynamicString(formattedDateTime)
        }
    }
}