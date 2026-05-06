package com.example.presentation.controllers

import com.example.domain.usecases.BlockCardUseCase
import com.example.presentation.mappers.toCardActionResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class CardController(private val blockCardUseCase: BlockCardUseCase) {

    suspend fun blockCard(call: ApplicationCall) {
        val cardId = call.parameters["id"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Card ID missing")

        val isSuccess = blockCardUseCase.execute(cardId)
        val response = toCardActionResponse(cardId, isSuccess)

        val status = if (isSuccess) HttpStatusCode.OK else HttpStatusCode.UnprocessableEntity
        call.respond(status, response)
    }
}