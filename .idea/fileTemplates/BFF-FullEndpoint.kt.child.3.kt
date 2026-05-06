package com.example.presentation.routes

import com.example.presentation.controllers.${NAME}Controller
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Route.${NAME.toLowerCase()}Routes() {
    val controller by inject<${NAME}Controller>()
    get("/mobile/${NAME.toLowerCase()}s") {
        controller.${NAME}(call)
    }
}