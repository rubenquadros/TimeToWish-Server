package io.github.rubenquadros.timetowish.db.internal.mappers

import io.github.rubenquadros.timetowish.db.model.DbEvent

fun Map<String, Any>.toDbEvent(): DbEvent {
    return DbEvent(
        id = this["id"].toString(),
        userId = this["userId"].toString(),
        title = this["title"].toString(),
        date = this["date"].toString(),
        updatedOn = this["updatedOn"].toString(),
        description = this["description"].toString(),
        reminderId = this["reminderId"] as? Long,
        reminderDateTime = this["reminderDateTime"].toString(),
        day = this["day"] as Long,
        month = this["month"] as Long,
        isActive = this["isActive"].toString()
    )
}