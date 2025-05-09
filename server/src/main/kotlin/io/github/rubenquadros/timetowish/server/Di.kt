package io.github.rubenquadros.timetowish.server

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.github.rubenquadros.timetowish.db.di.DatabaseModule
import io.ktor.server.application.*
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import java.io.File
import java.util.*

internal fun Application.configureDi() {
    install(Koin) {
        slf4jLogger()
        modules(listOf(ServerModule().module))
    }
}

@Module(includes = [DatabaseModule::class])
@ComponentScan("io.github.rubenquadros.timetowish.server")
class ServerModule {

    @Factory
    fun provideVerifier(): GoogleIdTokenVerifier {
        return GoogleIdTokenVerifier.Builder(
            NetHttpTransport(),
            GsonFactory()
        ).setAudience(listOf(getWebLoginKey())).build()
    }
}

private fun getWebLoginKey(): String {
    return runCatching {
        val properties = Properties().apply {
            File("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-Server/local.properties")
                .inputStream()
                .use { load(it) }
        }

        properties.getProperty("googleWebAuthKey")
    }.getOrElse {
        System.getenv("GOOGLE_AUTH_WEB_KEY")
    }
}