package io.github.rubenquadros.timetowish.server.login.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginKeysAndPagesResponse(
    val keys: LoginKeys,
    val pages: LoginPages = LoginPages()
)

@Serializable
internal data class LoginKeys(
    val google: PlatformLoginKeys
) {
    companion object {
        fun default() = LoginKeys(
            google = PlatformLoginKeys.defaultGoogleKeys()
        )
    }
}

@Serializable
internal data class PlatformLoginKeys(
    val android: String,
    val ios: String
) {
    companion object {
        fun defaultGoogleKeys() = PlatformLoginKeys(
            android = System.getenv("GOOGLE_AUTH_ANDROID_KEY"),
            ios = System.getenv("GOOGLE_AUTH_IOS_KEY")
        )
    }
}

@Serializable
internal data class LoginPages(
    val termsLink: String = "https://rubenquadros.github.io/TimeToWish/terms.html",
    val privacyLink: String = "https://rubenquadros.github.io/TimeToWish/privacy.html"
)