package io.github.rubenquadros.timetowish.server.core

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

internal data class Response(
    val status: HttpStatusCode = HttpStatusCode.OK,
    val data: Any
)

@Serializable
internal data class ErrorResult(
    val message: String
)