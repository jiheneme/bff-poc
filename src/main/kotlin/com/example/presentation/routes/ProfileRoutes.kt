package com.example.presentation.routes

import com.example.presentation.controllers.ProfileController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import io.ktor.server.application.*

fun Route.profileRoutes() {
    val controller by inject<ProfileController>()

    get("/mobile/profile") {
        controller.getProfile(call)
    }
}