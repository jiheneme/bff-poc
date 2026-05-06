package com.example.plugins

import com.example.domain.exceptions.RemoteServiceException
import com.example.domain.exceptions.UserNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

fun Application.configureStatusPages() {
    val logger = LoggerFactory.getLogger("ExceptionHandler")
    install(StatusPages) {

        exception<UserNotFoundException> { call, cause ->
            logger.warn("Business Error: User {} not found", cause.email)
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "User ${cause.email} not found"))
        }
        exception<RemoteServiceException> { call, cause ->
            logger.error("Dependency Error: Service {} failed with status {}", cause.serviceName, cause.statusCode)
            call.respond(HttpStatusCode.BadGateway, mapOf(
                "error" to "Service ${cause.serviceName} failed with status ${cause.statusCode}"
            ))
        }
        // Exceptions d'Infrastructure (Erreurs de connexion réseau)
        // On intercepte les erreurs réseau Java/Kotlin classiques
        exception<java.net.ConnectException> { call, _ ->
            logger.error("Network Error: Target microservice is unreachable")
            call.respond(HttpStatusCode.ServiceUnavailable, mapOf("error" to "Target microservice is unreachable"))
        }
        exception<java.nio.channels.UnresolvedAddressException> { call, _ ->
            call.respond(HttpStatusCode.ServiceUnavailable, mapOf("error" to "Microservice host could not be resolved"))
        }
        exception<io.ktor.serialization.JsonConvertException> { call, cause ->
            logger.error("Serialization Error: Incompatible JSON format", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Invalid data format received from internal services")
            )
        }
        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            logger.error("Unexpected Internal Error: ", cause)
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Internal server error"))
        }
    }
}