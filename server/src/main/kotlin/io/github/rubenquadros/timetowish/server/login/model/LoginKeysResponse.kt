package io.github.rubenquadros.timetowish.server.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginKeysResponse(
    val google: PlatformLoginKeys = PlatformLoginKeys.default
)

@Serializable
data class PlatformLoginKeys(
    val android: String,
    val ios: String
) {
    companion object {
        val default = PlatformLoginKeys(
            android = System.getenv("GOOGLE_AUTH_ANDROID_KEY"),
            ios = System.getenv("GOOGLE_AUTH_IOS_KEY")
        )
    }
}