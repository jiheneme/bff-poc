package com.example.presentation.routes

import com.example.domain.repository.CardRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.cardActionsRoutes(repository: CardRepository) {
        post("/mobile/cards/{id}/block") {
            val cardId = call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest)

            val isSuccess = repository.requestCardBlock(cardId)

            if (isSuccess) {
                call.respond(HttpStatusCode.OK, mapOf("status" to "blocked"))
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Erreur microservice")
            }
        }
    }