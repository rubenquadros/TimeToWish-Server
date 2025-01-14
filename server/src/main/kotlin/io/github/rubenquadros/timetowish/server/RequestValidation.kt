package io.github.rubenquadros.timetowish.server

import io.github.rubenquadros.timetowish.server.events.eventsValidation
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

internal fun Application.configureRequestValidation() {
    install(RequestValidation) {
        eventsValidation()
    }
}