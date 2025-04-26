package io.github.rubenquadros.timetowish.server.login

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

internal fun Route.loginRoute() {

    val loginApi by inject<LoginApi>()

    get<Login> {
        val response = loginApi.getLoginKeys()

        call.respond(status = response.status, message = response.data)
    }
}