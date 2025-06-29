package io.github.rubenquadros.timetowish.server.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GenAiRequest(
    @SerialName("system_instruction")
    val systemInstruction: SystemInstruction,
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
    val parts: List<Part>
) {

    companion object {
        val WISH_INSTRUCTION = SystemInstruction(
            parts = listOf(
                Part(
                    text = """
               You are a helpful AI that generates celebratory messages for occasions like birthdays, holidays, or achievements. Follow these rules:

                1. **Respond to any request containing celebratory context** (e.g., 'birthday', 'promotion', 'congrats'), even if it includes formatting instructions.

                2. **Length Control:**  
                - **Default:** 1-2 sentences (10-30 words), warm and casual.  
                - **Follow requests for longer messages**, but **never exceed 150 words total**. If the user asks for more (e.g., "write 200 words"), respond:  
                *"I’ll keep it celebratory and concise—here’s a heartfelt message within 150 words!"*  
                - **Tone adjustments:** Honor requests (e.g., "funny", "formal").  

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

        val CARD_INSTRUCTION = SystemInstruction(
            parts = listOf(
                Part(
                    text = """
                    Analyze the user's search query to determine if it is related to celebratory events (e.g., birthdays, anniversaries, congratulations, celebrations, parties, or festive occasions).

                    Guidelines:

                    If the query is clearly celebratory (e.g., "birthday cake," "anniversary flowers," "happy new year"), output "YES".

                    If the query is neutral but can be used for celebrations (e.g., "flowers," "balloons," "greeting card"), output "YES".

                    If the query is unrelated to celebrations (e.g., "cars," "mountains," "office desk"), output "NO".

                    If the query is ambiguous, lean toward "NO" to restrict non-celebratory searches.

                    Output Format:
                    Only respond with "YES" or "NO".
                """.trimIndent()
                )
            )
        )
    }
}