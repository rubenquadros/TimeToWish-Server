package io.github.rubenquadros.timetowish.server

import io.github.rubenquadros.timetowish.server.core.ApiException
import io.github.rubenquadros.timetowish.server.core.ErrorResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

internal fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            val status = when (throwable) {
                is ApiException -> throwable.statusCode
                is RequestValidationException -> HttpStatusCode.BadRequest
                else -> HttpStatusCode.InternalServerError
            }

            call.respond(status, ErrorResult(throwable.message.orEmpty()))
        }
    }
}