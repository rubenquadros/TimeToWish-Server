package io.github.rubenquadros.timetowish.server.wishes.model

import kotlinx.serialization.Serializable

@Serializable
internal data class GenAiResponse(
    val candidates: List<Candidate>,
    val usageMetaData: UsageMetaData?,
    val modelVersion: String
)

@Serializable
internal data class Candidate(
    val content: Content,
    val finishReason: String,
    val avgLogprobs: Double
)

@Serializable
internal data class UsageMetaData(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int,
    val promptTokensDetails: List<TokensDetail>,
    val candidatesTokensDetails: List<TokensDetail>
)

@Serializable
internal data class TokensDetail(
    val modality: String,
    val tokenCount: Int
)