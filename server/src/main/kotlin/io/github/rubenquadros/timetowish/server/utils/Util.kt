package io.github.rubenquadros.timetowish.server.utils

import io.github.rubenquadros.timetowish.server.utils.Constants.USER_ID_HEADER
import io.ktor.server.application.ApplicationCall
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun ApplicationCall.getUserId(): String {
    return this.request.headers[USER_ID_HEADER].orEmpty()
}

fun getDateNow(): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return getDateTimeToStringFormatter().format(today)
}

fun getDateTimeToStringFormatter(): DateTimeFormat<LocalDateTime> {
    return LocalDateTime.Format {
        dayOfMonth()
        char('-')
        monthNumber()
        char('-')
        year()
    }
}