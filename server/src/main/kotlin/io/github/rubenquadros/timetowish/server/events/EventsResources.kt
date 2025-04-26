package io.github.rubenquadros.timetowish.server.events

import io.ktor.resources.Resource

@Resource("/events")
internal data class Events(
    val date: String? = null
) {
    @Resource("/{id}/reminder")
    data class Reminder(val parent: Events = Events(), val id: String)
}