package io.github.rubenquadros.timetowish.db.model

data class DbUser(
    val userId: String,
    val name: String,
    val profilePic: String?,
    val email: String,
    val isDeleted: String = "N"
)
