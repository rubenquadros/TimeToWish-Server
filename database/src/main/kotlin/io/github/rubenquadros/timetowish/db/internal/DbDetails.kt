package io.github.rubenquadros.timetowish.db.internal

import java.io.FileInputStream
import java.util.Properties

internal data class DbDetails(
    val url: String = System.getenv("DATABASE_URL"),
    val adminAccessPath: String = "etc/secrets/admin.json"
)

internal fun getDbDetails(): DbDetails {
    var inputStream: FileInputStream? = null
    return try {
        inputStream = FileInputStream("/Users/rquadros/Documents/Ruben/git_tree/TimeToWish-Server/local.properties")
        val properties = Properties()
        properties.load(inputStream)
        val url = properties.getProperty("dbUrl")
        val adminPath = properties.getProperty("adminAccountPath")
        DbDetails(url = url, adminAccessPath = adminPath)
    } catch (_: Exception) {
        DbDetails()
    } finally {
        inputStream?.close()
        inputStream = null
    }
}