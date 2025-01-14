package io.github.rubenquadros.timetowish.db.model

import kotlinx.serialization.Serializable

@Serializable
data class DbEvent(
    val id: String,
    val userId: String,
    val date: String,
    val title: String,
    val description: String? = null,
    val updatedOn: String,
    val day: Long,
    val month: Long,
    val reminderId: Long? = null,
    val reminderDateTime: String? = null,
    val isActive: String
)