package io.github.rubenquadros.timetowish.server.login

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource("/login")
internal class Login {
    @Resource("keys")
    data class Keys(
        val parent: Login = Login()
    )

    @Resource("/validate")
    data class Validate(val parent: Login = Login())
}

@Serializable
data class LoginRequest(
    val token: String
)