package io.github.rubenquadros.timetowish.server

import io.github.rubenquadros.timetowish.server.events.eventsRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

internal fun Application.configureRouting() {
    routing {
        eventsRoute()
    }
}