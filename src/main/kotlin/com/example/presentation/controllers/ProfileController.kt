package com.example.presentation.controllers

import com.example.domain.usecases.GetProfileUseCase
import com.example.presentation.mappers.toMobileProfileResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class ProfileController(private val getProfileUseCase: GetProfileUseCase) {

    suspend fun getProfile(call: ApplicationCall) {
        val email = call.parameters["email"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Email is required")

        // Ici tu peux ajouter ton monitoring : timer.start(), log.info("Fetching profile for $email")
        val (user, cards) = getProfileUseCase.execute(email)

        val response = toMobileProfileResponse(user, cards)
        call.respond(response)
    }
}