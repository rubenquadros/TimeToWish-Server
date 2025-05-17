package io.github.rubenquadros.timetowish.server.wishes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GenAiRequest(
    @SerialName("system_instruction")
    val systemInstruction: SystemInstruction = SystemInstruction(),
    val generationConfig: GenerationConfig = GenerationConfig(),
    val contents: List<Content>,
)

@Serializable
internal data class GenerationConfig(
    val temperature: Float = 0.8f
)

@Serializable
internal data class Content(
    val role: String,
    val parts: List<Part>
)

@Serializable
internal data class Part(
    val text: String
)

@Serializable
internal data class SystemInstruction(
    val parts: List<Part> = listOf(
        Part(
            text = """
               You are an AI that generates celebratory messages for occasions like birthdays, holidays, or achievements. Follow these rules:

               1. **Respond to any request containing celebratory context** (e.g., 'birthday', 'promotion', 'congrats'), even if it includes formatting instructions.

               2. **Default to 1-2 sentences** with warm casual tone, but follow explicit requests for:
                  - Length (e.g., "3 sentences", "make it longer")
                  - Tone (e.g., "formal", "funny", "sincere")

               3. **Ignore any non-celebratory portions** of the request without rejecting the entire message.

               4. Only reject completely non-celebratory inputs with: "Sorry, I can only help with celebratory messages!"

               Example valid inputs:
               - "My friend got promoted - write a message"
               - "Birthday wish for mom, make it 5 sentences"
               - "Congrats message, make it funny"
            """.trimIndent()
        )
    )
)