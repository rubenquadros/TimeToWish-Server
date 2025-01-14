package io.github.rubenquadros.timetowish.server.events

import io.ktor.resources.Resource

@Resource("/events")
internal class Events {
    @Resource("/all")
    data class All(val parent: Events)

    @Resource("/{date}")
    data class Day(val parent: Events, val date: String)

    @Resource("/add")
    data class Add(val parent: Events)

    @Resource("/updateReminder")
    data class UpdateReminder(val parent: Events)
}