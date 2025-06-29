package io.github.rubenquadros.timetowish.server.cards.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ImageResponse(
    @SerialName("id")
    val id: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("color")
    val color: String,
    @SerialName("blur_hash")
    val blurHash: String?,
    @SerialName("likes")
    val likes: Long,
    @SerialName("description")
    val description: String?,
    @SerialName("urls")
    val urls: CardUrls,
    @SerialName("links")
    val links: CardLinks
)

@Serializable
internal data class CardUrls(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
)

@Serializable
internal data class CardLinks(
    @SerialName("self")
    val self: String?,
    @SerialName("html")
    val html: String?,
    @SerialName("download")
    val download: String?,
    @SerialName("download_location")
    val downloadLocation: String?
)
