package io.github.rubenquadros.timetowish.server.cards

import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

internal fun Route.cardsRoute() {

    val cardsService by inject<CardsService>()

    get<GetCards> {
        val response = cardsService.getCards(
            query = it.query,
            page = it.page,
            limit = it.limit
        )

        call.respond(status = response.status, message = response.data)
    }
}