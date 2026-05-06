package com.example.presentation.controllers

import com.example.domain.usecases.GetProfileUseCase
import com.example.presentation.mappers.toMobileProfileResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

class ProfileController(private val getProfileUseCase: GetProfileUseCase) {

    private val logger = LoggerFactory.getLogger(ProfileController::class.java)

    suspend fun getProfile(call: ApplicationCall) {
        val email = call.parameters["email"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Email is required")

        logger.info("Request: Fetching profile for email: {}", email)

        val startTime = System.currentTimeMillis()

        val (user, cards) = getProfileUseCase.execute(email)

        val response = toMobileProfileResponse(user, cards)
        val duration = System.currentTimeMillis() - startTime

        logger.info("Success: Profile for {} fetched in {}ms", email, duration)
        call.respond(response)
    }
}