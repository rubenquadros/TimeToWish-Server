package io.github.rubenquadros.timetowish.server

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.github.rubenquadros.timetowish.db.di.DatabaseModule
import io.github.rubenquadros.timetowish.server.core.httpClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.server.application.*
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
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

    @Single @Named("gemini")
    fun provideGeminiHttpClient(): HttpClient {
        return httpClient.config {
            defaultRequest {
                url {
                    host = "generativelanguage.googleapis.com/v1beta"
                    protocol = URLProtocol.HTTPS
                }

                contentType(ContentType.Application.Json)
            }
        }
    }

    @Single @Named("unsplash")
    fun provideUnsplashHttpClient(): HttpClient {
        return httpClient.config {
            defaultRequest {
                url {
                    host = "api.unsplash.com"
                    protocol = URLProtocol.HTTPS
                }

                headers {
                    set("Accept-Version", "v1")
                    set(HttpHeaders.Authorization, "Client-ID ${getUnsplashApiKey()}")
                }

                contentType(ContentType.Application.Json)
            }
        }
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

private fun getUnsplashApiKey(): String {
    return runCatching {
        val properties = Properties().apply {
            File("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-Server/local.properties")
                .inputStream()
                .use { load(it) }
        }

        properties.getProperty("unsplashApiKey")
    }.getOrElse {
        System.getenv("UNPLASH_API_KEY")
    }
}