package io.github.rubenquadros.timetowish.server.wishes

import io.github.rubenquadros.timetowish.server.wishes.model.Content
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

internal fun Route.wishesRoute() {

    val wishesService by inject<WishesService>()

    post<GenerateWish> {
        val body = call.receive<List<Content>>()
        val response = wishesService.generateContent(body)

        call.respond(status = response.status, message = response.data)
    }
}