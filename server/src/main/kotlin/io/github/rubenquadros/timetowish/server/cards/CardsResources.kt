package io.github.rubenquadros.timetowish.server.cards

import io.ktor.resources.Resource

@Resource("/cards")
internal data class GetCards(
    val query: String? = null,
    val page: Int? = null,
    val limit: Int? = null
)