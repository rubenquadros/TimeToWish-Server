package io.github.rubenquadros.timetowish.server.cards

import io.github.rubenquadros.timetowish.server.ai.*
import io.github.rubenquadros.timetowish.server.cards.model.CardsResponse
import io.github.rubenquadros.timetowish.server.cards.model.UnsplashResponse
import io.github.rubenquadros.timetowish.server.core.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

internal interface CardsService {
    suspend fun getCards(query: String?, page: Int?, limit: Int?): Response
}

@Single
internal class CardsServiceImpl(
    @Named("gemini") private val geminiClient: HttpClient,
    @Named("unsplash") private val unsplashClient: HttpClient
) : CardsService {
    override suspend fun getCards(
        query: String?,
        page: Int?,
        limit: Int?
    ): Response {

        if (query != null && (page == null || page == 1)) {
            val config = getGeminiConfig()
            val response = geminiClient.post {
                val apiRequest = GenAiRequest(
                    systemInstruction = SystemInstruction.CARD_INSTRUCTION,
                    contents = listOf(Content(
                        role = "user",
                        parts = listOf(Part(text = "User Query: $query"))
                    ))
                )
                url {
                    appendPathSegments("/${config.model}:generateContent")
                    parameters.append("key", config.apiKey)
                }
                setBody(apiRequest)
            }.body<GenAiResponse>()

            val isValidQuery = runCatching {
                response.candidates.first().content.parts.first().text.trim() == "YES"
            }.getOrDefault(false)

            if (!isValidQuery) {
                return Response(
                    status = HttpStatusCode.OK,
                    data = CardsResponse(
                        queryRejected = true,
                        results = null
                    )
                )
            }
        }

        //call unsplash api to get the cards
        val unsplashResponse = unsplashClient.get {
            url {
                appendPathSegments("/search/photos")
                parameters.apply {
                    append("query", query ?: "happy birthday")
                    append("page", page?.toString() ?: "1")
                    append("per_page", limit?.toString() ?: "20")
                }
            }
        }.body<UnsplashResponse>()

        return Response(
            status = HttpStatusCode.OK,
            data = CardsResponse(
                queryRejected = false,
                results = unsplashResponse.results
            )
        )
    }
}