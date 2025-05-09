package io.github.rubenquadros.timetowish.db

import com.google.cloud.firestore.*
import io.github.rubenquadros.timetowish.db.internal.DbConstants.EVENTS
import io.github.rubenquadros.timetowish.db.internal.DbConstants.USERS
import io.github.rubenquadros.timetowish.db.internal.DbConstants.USER_EVENTS
import io.github.rubenquadros.timetowish.db.internal.mappers.toDbEvent
import io.github.rubenquadros.timetowish.db.model.DbEvent
import io.github.rubenquadros.timetowish.db.model.DbUser
import io.github.rubenquadros.timetowish.db.model.WriteResponse
import org.koin.core.annotation.Single

@Single
internal class FirebaseApi(private val firestore: Firestore): DbOperations {

    override suspend fun getAllEvents(userId: String): List<DbEvent> {
        return firestore.collection(EVENTS).document(userId).collection(USER_EVENTS).listDocuments().mapNotNull { documents ->
            val documentSnapshot: DocumentSnapshot = documents.get().await()
            documentSnapshot.data?.toDbEvent()
        }
    }

    override suspend fun getEvents(
        userId: String,
        date: String
    ): List<DbEvent> {
        val dateList = date.split("-")
        val query: Query =  firestore.collection(EVENTS).document(userId).collection(USER_EVENTS)
            .whereEqualTo("month", dateList[1].toLong())
            .whereEqualTo("day", dateList[0].toLong())

        val querySnapshot: QuerySnapshot = query.get().await()
        return querySnapshot.documents.map { queryDocumentSnapshot: QueryDocumentSnapshot ->
            queryDocumentSnapshot.data.toDbEvent()
        }
    }

    override suspend fun addEvent(dbEvent: DbEvent): WriteResponse {
        firestore.collection(EVENTS).document(dbEvent.userId).collection(USER_EVENTS)
            .document(dbEvent.id).set(dbEvent).await()

        return WriteResponse
    }

    override suspend fun saveUser(dbUser: DbUser): WriteResponse {
        firestore.collection(USERS).document(dbUser.userId).set(dbUser).await()

        return WriteResponse
    }
}