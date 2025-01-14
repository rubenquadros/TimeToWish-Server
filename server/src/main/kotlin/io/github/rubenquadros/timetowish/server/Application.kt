package io.github.rubenquadros.timetowish.server

import io.github.rubenquadros.timetowish.server.core.configureHeaderValidation
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

private fun Application.module() {
    configureDi()
    configureSerialization()
    configureResources()
    configureRouting()
    configureHeaderValidation()
    configureRequestValidation()
    configureStatusPages()
}