package com.example.presentation.routes

import com.example.domain.usecases.GetProfileUseCase
import com.example.presentation.dtos.MobileCardDTO
import com.example.presentation.dtos.responses.MobileProfileResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject

fun Route.profileRoutes() {
    val getProfileUseCase by inject<GetProfileUseCase>()
        get("/mobile/profile") {
            val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest)

            val (user, cards) = getProfileUseCase.execute(email)

            // Mapping Entity -> DTO Mobile
            val response = MobileProfileResponse(
                userName = user.name,
                cards = cards.map {
                    MobileCardDTO(it.id, "Carte **** ${it.lastFour}", it.status == "ACTIVE")
                }
            )
            call.respond(response)
        }
    }
