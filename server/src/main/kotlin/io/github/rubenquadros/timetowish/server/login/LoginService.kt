package io.github.rubenquadros.timetowish.server.login

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import io.github.rubenquadros.timetowish.db.DbOperations
import io.github.rubenquadros.timetowish.server.core.ApiException
import io.github.rubenquadros.timetowish.server.core.Response
import io.github.rubenquadros.timetowish.server.login.model.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.io.File
import java.util.*

internal interface LoginService {
    suspend fun getLoginKeysAndPages(): Response

    suspend fun getLoginResult(token: String): Response
}

@Single
internal class LoginServiceImpl(
    private val verifier: GoogleIdTokenVerifier,
    private val dbOperations: DbOperations
) : LoginService {
    override suspend fun getLoginKeysAndPages(): Response {
        val result = getLoginKeysInternal()

        return Response(data = result)
    }

    override suspend fun getLoginResult(token: String): Response {
        val googleIdToken = verifier.verify(token)

        if (googleIdToken != null) {
            val payload = googleIdToken.payload
            val response = LoginValidationResponse(
                id = payload.subject,
                email = payload.email,
                profilePic = payload.get("picture") as? String,
                name = payload.get("name") as? String ?: ""
            )

            dbOperations.saveUser(response.toDbUser())

            return Response(data = response)
        } else {
            throw ApiException(
                statusCode = HttpStatusCode.InternalServerError,
                message = "Invalid token"
            )
        }
    }

    private suspend fun getLoginKeysInternal(): LoginKeysAndPagesResponse {
        return withContext(Dispatchers.IO) {
            runCatching {
                val properties = Properties().apply {
                    File("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-Server/local.properties")
                        .inputStream()
                        .use { load(it) }
                }

                LoginKeysAndPagesResponse(
                    keys = LoginKeys(
                        google = PlatformLoginKeys(
                            android = properties.getProperty("googleAndroidAuthKey"),
                            ios = properties.getProperty("googleIosAuthKey")
                        )
                    )
                )
            }.getOrElse {
                LoginKeysAndPagesResponse(keys = LoginKeys.default())
            }
        }
    }
}