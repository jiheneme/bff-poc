package com.example.plugins

import com.example.domain.exceptions.RemoteServiceException
import com.example.domain.exceptions.UserNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {

        // Si on attrape une UserNotFoundException -> 404
        exception<UserNotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "User ${cause.email} not found"))
        }

        // Si un microservice répond une erreur -> 502 (Bad Gateway)
        exception<RemoteServiceException> { call, cause ->
            call.respond(HttpStatusCode.BadGateway, mapOf(
                "error" to "Service ${cause.serviceName} failed with status ${cause.statusCode}"
            ))
        }

        // Erreur générique pour tout le reste -> 500
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "An unexpected error occurred"))
        }
    }
}