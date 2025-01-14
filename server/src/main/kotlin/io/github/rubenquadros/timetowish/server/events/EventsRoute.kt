package io.github.rubenquadros.timetowish.server.events

import io.github.rubenquadros.timetowish.server.utils.getUserId
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

internal fun Route.eventsRoute() {

    val eventsApi by inject<EventsApi>()

    get<Events.All> {
        val response = eventsApi.getAllEvents(userId = call.getUserId())
        call.respond(status = response.status, message = response.data)
    }

    get<Events.Day> { day ->
        val response = eventsApi.getEvents(
            userId = call.getUserId(),
            date = day.date
        )

        call.respond(status = response.status, message = response.data)
    }

    post<Events.Add> {
        val body = call.receive<ServerEvent>()
        val response = eventsApi.addEvent(body)

        call.respond(response.status)
    }
}