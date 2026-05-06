package com.example.plugins

import com.example.domain.exceptions.RemoteServiceException
import com.example.domain.exceptions.UserNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {

        exception<UserNotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "User ${cause.email} not found"))
        }

        exception<RemoteServiceException> { call, cause ->
            call.respond(HttpStatusCode.BadGateway, mapOf(
                "error" to "Service ${cause.serviceName} failed with status ${cause.statusCode}"
            ))
        }

        // Exceptions d'Infrastructure (Erreurs de connexion réseau)
        // On intercepte les erreurs réseau Java/Kotlin classiques
        exception<java.net.ConnectException> { call, _ ->
            call.respond(HttpStatusCode.ServiceUnavailable, mapOf("error" to "Target microservice is unreachable"))
        }

        exception<java.nio.channels.UnresolvedAddressException> { call, _ ->
            call.respond(HttpStatusCode.ServiceUnavailable, mapOf("error" to "Microservice host could not be resolved"))
        }

        exception<io.ktor.serialization.JsonConvertException> { call, _ ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Invalid data format received from internal services")
            )
        }

        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Internal server error"))
        }
    }
}