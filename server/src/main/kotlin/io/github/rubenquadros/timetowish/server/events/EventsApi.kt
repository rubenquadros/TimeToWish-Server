package io.github.rubenquadros.timetowish.server.events

import io.github.rubenquadros.timetowish.db.DbOperations
import io.github.rubenquadros.timetowish.server.core.Response
import org.koin.core.annotation.Single

internal interface EventsApi {
    suspend fun getAllEvents(userId: String): Response
    suspend fun getEvents(userId: String, date: String): Response
    suspend fun addEvent(serverEvent: ServerEvent): Response
}

@Single
internal class EventsAPiImpl(
    private val dbOperations: DbOperations
) : EventsApi {
    override suspend fun getAllEvents(userId: String): Response {
        val result = dbOperations.getAllEvents(userId)

        //format the events -
        //1. we sort the events based on months (Jan to Dec)
        //2. we group the events based on months
        //3. now we again sort each month events by days (01 to 31)
        //4. we then group the events based on days
        //finally, we have -> [Month[Day[Event for that day]]]
        val formattedResult = result.sortedBy { dbEvent ->
            dbEvent.month
        }.groupBy { dbEvent ->
            dbEvent.month
        }.map { (_, monthEvent) ->
            monthEvent.sortedBy { dbEvent ->
                dbEvent.day
            }.groupBy { dbEvent ->
                dbEvent.day
            }.map { (_, day) ->
                day
            }
        }

        return Response(data = formattedResult)
    }

    override suspend fun getEvents(userId: String, date: String): Response {
        val result = dbOperations.getEvents(userId, date)

        return Response(data = result)
    }

    override suspend fun addEvent(serverEvent: ServerEvent): Response {
        val result = dbOperations.addEvent(
            dbEvent = serverEvent.toDbEvent()
        )

        return Response(data = result)
    }
}