package io.github.rubenquadros.timetowish.server

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

internal fun Application.configureDi() {
    install(Koin) {
        slf4jLogger()
        modules(listOf(ServerModule().module))
    }
}

@Module
@ComponentScan("io.github.rubenquadros.timetowish.server")
class ServerModule