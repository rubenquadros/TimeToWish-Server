package io.github.rubenquadros.timetowish.server.login

import io.github.rubenquadros.timetowish.server.core.Response
import io.github.rubenquadros.timetowish.server.login.model.LoginKeysResponse
import io.github.rubenquadros.timetowish.server.login.model.PlatformLoginKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.io.File
import java.util.Properties

internal interface LoginApi {
    suspend fun getLoginKeys(): Response
}

@Single
internal class LoginApiImpl : LoginApi {
    override suspend fun getLoginKeys(): Response {
        val result = getLoginKeysInternal()

        return Response(data = result)
    }

    private suspend fun getLoginKeysInternal(): LoginKeysResponse {
        return withContext(Dispatchers.IO) {
            runCatching {
                val properties = Properties().apply {
                    File("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-Server/local.properties")
                        .inputStream()
                        .use { load(it) }
                }
                LoginKeysResponse(
                    google = PlatformLoginKeys(
                        android = properties.getProperty("googleAndroidAuthKey"),
                        ios = properties.getProperty("googleIosAuthKey")
                    )
                )
            }.getOrDefault(LoginKeysResponse())
        }
    }
}