package io.github.rubenquadros.timetowish.server.core

import io.github.rubenquadros.timetowish.server.utils.Constants.DEVICE_ID_HEADER
import io.github.rubenquadros.timetowish.server.utils.Constants.USER_ID_HEADER
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.util.*

internal class HeaderValidation() {

    companion object Plugin: BaseApplicationPlugin<ApplicationCallPipeline, Unit, HeaderValidation> {
        override val key: AttributeKey<HeaderValidation>
            get() = AttributeKey<HeaderValidation>("HeaderValidation")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: Unit.() -> Unit
        ): HeaderValidation {
            val plugin = HeaderValidation()

            pipeline.intercept(ApplicationCallPipeline.Plugins) {
                val message = StringBuilder("")
                val headers = call.request.headers
                val userId = headers[USER_ID_HEADER]
                val deviceId = headers[DEVICE_ID_HEADER]
                if (userId.isNullOrEmpty()) {
                    message.append("Missing required header: $USER_ID_HEADER; ")
                }

                if (deviceId.isNullOrEmpty()) {
                    message.append("Missing required header: $DEVICE_ID_HEADER; ")
                }

                if (message.toString().isNotEmpty()) {
                    //some headers are missing
                    throw ApiException(
                        statusCode = HttpStatusCode.BadRequest,
                        message = message.toString()
                    )
                }
            }

            return plugin
        }
    }
}

internal fun Application.configureHeaderValidation() {
    install(HeaderValidation)
}