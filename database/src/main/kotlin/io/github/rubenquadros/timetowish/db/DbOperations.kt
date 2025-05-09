package io.github.rubenquadros.timetowish.db

import io.github.rubenquadros.timetowish.db.model.DbEvent
import io.github.rubenquadros.timetowish.db.model.DbUser
import io.github.rubenquadros.timetowish.db.model.WriteResponse

interface DbOperations {
    suspend fun getAllEvents(userId: String): List<DbEvent>

    suspend fun getEvents(userId: String, date: String): List<DbEvent>

    suspend fun addEvent(dbEvent: DbEvent): WriteResponse

    suspend fun saveUser(dbUser: DbUser): WriteResponse
}