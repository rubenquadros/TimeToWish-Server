package io.github.rubenquadros.timetowish.server

import io.github.rubenquadros.timetowish.server.cards.cardsRoute
import io.github.rubenquadros.timetowish.server.events.eventsRoute
import io.github.rubenquadros.timetowish.server.login.loginRoute
import io.github.rubenquadros.timetowish.server.wishes.wishesRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

internal fun Application.configureRouting() {
    routing {
        eventsRoute()
        loginRoute()
        wishesRoute()
        cardsRoute()
    }
}