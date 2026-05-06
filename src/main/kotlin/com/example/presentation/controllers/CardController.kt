package com.example.presentation.controllers

import com.example.domain.usecases.BlockCardUseCase
import com.example.presentation.mappers.toCardActionResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory

class CardController(private val blockCardUseCase: BlockCardUseCase) {
    private val logger = LoggerFactory.getLogger(CardController::class.java)

    suspend fun blockCard(call: ApplicationCall) {
        val cardId = call.parameters["id"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Card ID missing")

        logger.info("Request: Action BLOCK for cardId: {}", cardId)
        val startTime = System.currentTimeMillis()

        val isSuccess = blockCardUseCase.execute(cardId)
        val response = toCardActionResponse(cardId, isSuccess)

        val duration = System.currentTimeMillis() - startTime

        if (isSuccess) {
            logger.info("Success: Card {} blocked in {}ms", cardId, duration)
            call.respond(HttpStatusCode.OK, response)
        } else {
            logger.warn("Business Failure: Card {} could not be blocked (Duration: {}ms)", cardId, duration)
            call.respond(HttpStatusCode.UnprocessableEntity, response)
        }
    }
}