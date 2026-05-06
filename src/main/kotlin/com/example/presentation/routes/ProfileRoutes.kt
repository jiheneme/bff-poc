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
        val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Email is required")

        // Appel au Use Case (si ça throw, StatusPages intercepe) mais dans le cas d'un échec métier onntraite l'erreur ici
        val (user, cards) = getProfileUseCase.execute(email)
        val response = toMobileProfileResponse(user, cards)

        call.respond(response)
    }
}