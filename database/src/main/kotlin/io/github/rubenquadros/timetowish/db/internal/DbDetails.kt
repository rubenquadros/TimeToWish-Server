package io.github.rubenquadros.timetowish.db.internal

import java.io.File
import java.util.*

internal data class DbDetails(
    val url: String = System.getenv("DATABASE_URL"),
    val adminAccessPath: String = "etc/secrets/admin.json"
)

internal fun getDbDetails(): DbDetails {
    return runCatching {
        val properties = Properties().apply {
            File("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-Server/local.properties")
                .inputStream()
                .use { load(it) }
        }

        DbDetails(
            url = properties.getProperty("dbUrl"),
            adminAccessPath = properties.getProperty("adminAccountPath")
        )

    }.getOrDefault(DbDetails())
}