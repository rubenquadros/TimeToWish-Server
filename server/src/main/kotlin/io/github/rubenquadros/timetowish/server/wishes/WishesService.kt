package io.github.rubenquadros.timetowish.server.wishes

import io.github.rubenquadros.timetowish.server.core.Response
import io.github.rubenquadros.timetowish.server.core.httpClient
import io.github.rubenquadros.timetowish.server.wishes.model.Content
import io.github.rubenquadros.timetowish.server.wishes.model.GeminiConfig
import io.github.rubenquadros.timetowish.server.wishes.model.GenAiRequest
import io.github.rubenquadros.timetowish.server.wishes.model.GenAiResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.annotation.Single
import java.io.File
import java.util.*

internal interface WishesService {
    suspend fun generateContent(contents: List<Content>): Response
}

@Single
internal class WishServiceImpl : WishesService {

    private val client by lazy {
        httpClient.config {
            defaultRequest {
                url {
                    host = "generativelanguage.googleapis.com/v1beta"
                    protocol = URLProtocol.HTTPS
                }

                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun generateContent(contents: List<Content>): Response {
        val config = getGeminiConfig()
        val response = client.post {
            val apiRequest = GenAiRequest(contents = contents)
            url {
                appendPathSegments("/${config.model}:generateContent")
                parameters.append("key", config.apiKey)
            }
            setBody(apiRequest)
        }.body<GenAiResponse>()

        return Response(data = response.candidates)

    }

    private fun getGeminiConfig(): GeminiConfig {
        return runCatching {
            val properties = Properties().apply {
                File("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-Server/local.properties")
                    .inputStream()
                    .use { load(it) }
            }

            return GeminiConfig(
                apiKey = properties.getProperty("geminiApiKey"),
                model = properties.getProperty("geminiModel"),
            )
        }.getOrElse {
            GeminiConfig(
                apiKey = System.getenv("GEMINI_API_KEY"),
                model = System.getenv("GEMINI_MODEL")
            )
        }
    }
}