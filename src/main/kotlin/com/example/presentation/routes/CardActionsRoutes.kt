package com.example.presentation.routes

import com.example.domain.usecases.BlockCardUseCase
import com.example.presentation.mappers.toCardActionResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.cardActionsRoutes() {
    val blockCardUseCase by inject<BlockCardUseCase>()

    post("/mobile/cards/{id}/block") {
        val cardId = call.parameters["id"]
            ?: return@post call.respond(HttpStatusCode.BadRequest, "Card ID missing")

        try {
            val isSuccess = blockCardUseCase.execute(cardId)
            val response = toCardActionResponse(cardId, isSuccess)

            val status = if (isSuccess) HttpStatusCode.OK else HttpStatusCode.UnprocessableEntity
            call.respond(status, response)

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                toCardActionResponse(cardId, false).copy(message = "External service error")
            )
        }
    }
}