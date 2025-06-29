package io.github.rubenquadros.timetowish.server.ai

import java.io.File
import java.util.Properties

internal data class GeminiConfig(
    val apiKey: String,
    val model: String
)

internal fun getGeminiConfig(): GeminiConfig {
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