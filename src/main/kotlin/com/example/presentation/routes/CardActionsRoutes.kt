package com.example.presentation.routes

import com.example.presentation.controllers.CardController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.cardActionsRoutes() {
    val controller by inject<CardController>()

    post("/mobile/cards/{id}/block") {
        controller.blockCard(call)
    }
}