package io.github.rubenquadros.timetowish.server.cards.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CardsResponse(
    val results: List<ImageResponse>?,
    val queryRejected: Boolean
)
