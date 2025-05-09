package io.github.rubenquadros.timetowish.server.login.model

import io.github.rubenquadros.timetowish.db.model.DbUser
import kotlinx.serialization.Serializable

@Serializable
internal data class LoginValidationResponse(
    val id: String,
    val name: String,
    val email: String,
    val profilePic: String?
)

internal fun LoginValidationResponse.toDbUser(): DbUser {
    return DbUser(
        userId = id,
        name = name,
        email = email,
        profilePic = profilePic
    )
}