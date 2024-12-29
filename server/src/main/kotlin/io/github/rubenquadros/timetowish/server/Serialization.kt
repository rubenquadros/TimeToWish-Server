package io.github.rubenquadros.timetowish.server

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

internal fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
                encodeDefaults = true
                explicitNulls = false
            }
        )
    }
}