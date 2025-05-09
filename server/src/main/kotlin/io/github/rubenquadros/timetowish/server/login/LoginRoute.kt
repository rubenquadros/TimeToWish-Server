package io.github.rubenquadros.timetowish.server.login

import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

internal fun Route.loginRoute() {

    val loginService by inject<LoginService>()

    get<Login.Keys> {
        val response = loginService.getLoginKeysAndPages()

        call.respond(status = response.status, message = response.data)
    }

    post<Login.Validate> {
        val body = call.receive<LoginRequest>()
        val response = loginService.getLoginResult(body.token)

        call.respond(status = response.status, message = response.data)
    }
}