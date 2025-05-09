package io.github.rubenquadros.timetowish.db.di

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.github.rubenquadros.timetowish.db.internal.getDbDetails
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import java.io.FileInputStream

@Module
@ComponentScan("io.github.rubenquadros.timetowish.db")
class DatabaseModule {

    @Factory
    fun provideFirestore(): Firestore {
        val dbDetails = getDbDetails()
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(FileInputStream(dbDetails.adminAccessPath)))
            .setDatabaseUrl(dbDetails.url)
            .build()

        val app = FirebaseApp.initializeApp(options)

        return FirestoreClient.getFirestore(app)
    }
}