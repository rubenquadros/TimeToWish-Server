package io.github.rubenquadros.timetowish.server.cards.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UnsplashResponse(
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val pages: Int,
    @SerialName("results")
    val results: List<ImageResponse>
)