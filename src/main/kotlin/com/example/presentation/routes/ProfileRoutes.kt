package com.example.presentation.routes

import com.example.domain.usecases.GetProfileUseCase
import com.example.presentation.mappers.toMobileProfileResponse
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.profileRoutes() {
    val getProfileUseCase by inject<GetProfileUseCase>()

    get("/mobile/profile") {
        val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Email requis")

        val (user, cards) = getProfileUseCase.execute(email)

        val response = toMobileProfileResponse(user, cards)

        call.respond(response)
    }
}