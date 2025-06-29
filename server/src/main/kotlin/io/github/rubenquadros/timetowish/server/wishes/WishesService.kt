package io.github.rubenquadros.timetowish.server.wishes

import io.github.rubenquadros.timetowish.server.ai.Content
import io.github.rubenquadros.timetowish.server.ai.GenAiRequest
import io.github.rubenquadros.timetowish.server.ai.GenAiResponse
import io.github.rubenquadros.timetowish.server.ai.SystemInstruction
import io.github.rubenquadros.timetowish.server.ai.getGeminiConfig
import io.github.rubenquadros.timetowish.server.core.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

internal interface WishesService {
    suspend fun generateContent(contents: List<Content>): Response
}

@Single
internal class WishServiceImpl(
    @Named("gemini") private val client: HttpClient
) : WishesService {

    override suspend fun generateContent(contents: List<Content>): Response {
        val config = getGeminiConfig()
        val response = client.post {
            val apiRequest = GenAiRequest(
                systemInstruction = SystemInstruction.WISH_INSTRUCTION,
                contents = contents
            )
            url {
                appendPathSegments("/${config.model}:generateContent")
                parameters.append("key", config.apiKey)
            }
            setBody(apiRequest)
        }.body<GenAiResponse>()

        return Response(data = response.candidates)

    }
}