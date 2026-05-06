package com.example.presentation.routes

import com.example.domain.usecases.BlockCardUseCase
import com.example.presentation.mappers.toCardActionResponse
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.cardActionsRoutes() {
    val blockCardUseCase by inject<BlockCardUseCase>()

    post("/mobile/cards/{id}/block") {
        val cardId = call.parameters["id"]
            ?: return@post call.respond(HttpStatusCode.BadRequest, "Card ID missing")

        val isSuccess = blockCardUseCase.execute(cardId)
        val response = toCardActionResponse(cardId, isSuccess)

        // On garde cette logique ici car le 422 est un résultat métier (carte qui ne peut pas être bloquée car dejà bloquée par ex et pas une exception technique)
        val status = if (isSuccess) HttpStatusCode.OK else HttpStatusCode.UnprocessableEntity

        call.respond(status, response)
    }
}