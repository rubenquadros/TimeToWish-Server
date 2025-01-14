package io.github.rubenquadros.timetowish.server.events

import io.github.rubenquadros.timetowish.db.model.DbEvent
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

internal fun RequestValidationConfig.eventsValidation() {
    validate<DbEvent> { body -> //Validate /events/add
        val message = StringBuilder("")
        if (body.id.isEmpty()) {
            message.append("id is required; ")
        }

        if (body.userId.isEmpty()) {
            message.append("userId is required; ")
        }

        if (body.title.isEmpty()) {
            message.append("title is required; ")
        }

        if (message.toString().isNotEmpty()) {
            ValidationResult.Invalid(message.toString())
        } else ValidationResult.Valid
    }
}