package io.github.rubenquadros.timetowish.server.core

import io.ktor.http.HttpStatusCode

internal class ApiException(val statusCode: HttpStatusCode, override val message: String): Exception(message)