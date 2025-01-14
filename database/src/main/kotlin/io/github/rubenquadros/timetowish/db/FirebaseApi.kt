package io.github.rubenquadros.timetowish.db

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.Query
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.github.rubenquadros.timetowish.db.internal.DbConstants.EVENTS
import io.github.rubenquadros.timetowish.db.internal.DbConstants.USER_EVENTS
import io.github.rubenquadros.timetowish.db.internal.getDbDetails
import io.github.rubenquadros.timetowish.db.internal.mappers.toDbEvent
import io.github.rubenquadros.timetowish.db.model.DbEvent
import io.github.rubenquadros.timetowish.db.model.WriteResponse
import org.koin.core.annotation.Single
import java.io.FileInputStream

@Single
internal class FirebaseApi: DbOperations {

    private val firestore: Firestore by lazy {
        val dbDetails = getDbDetails()
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(FileInputStream(dbDetails.adminAccessPath)))
            .setDatabaseUrl(dbDetails.url)
            .build()

        val app = FirebaseApp.initializeApp(options)

        FirestoreClient.getFirestore(app)
    }

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
}