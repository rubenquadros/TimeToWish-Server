package io.github.rubenquadros.timetowish.server.events

import io.github.rubenquadros.timetowish.db.model.DbEvent
import io.github.rubenquadros.timetowish.server.utils.getDateNow
import kotlinx.serialization.Serializable

/**
 * This class is used as the REQUEST and RESPONSE for clients
 */
@Serializable
internal data class ServerEvent(
    val id: String,
    val userId: String,
    val date: String,
    val title: String,
    val updatedOn: String = "",
    val description: String? = null,
    val reminderId: Long? = null,
    val reminderDateTime: String? = null,
)

internal fun ServerEvent.toDbEvent(): DbEvent {
    val dayMonthYear = date.split("-")
    return DbEvent(
        id = id,
        userId = userId,
        date = date,
        day = dayMonthYear[0].toLong(),
        month = dayMonthYear[1].toLong(),
        updatedOn = getDateNow(),
        title = title,
        description = description,
        reminderId = reminderId,
        reminderDateTime = reminderDateTime,
        isActive = "Y"
    )
}