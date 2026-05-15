package com.example

import com.example.plugins.*
import com.example.presentation.routes.cardActionsRoutes
import com.example.presentation.routes.profileRoutes
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {

    configureSerialization()
    configureMonitoring()
    configureStatusPages()
    configureCompression()
    configureCaching()

    if (!testing) {
        configureDependencyInjection()
    }

    routing {
        profileRoutes()
        cardActionsRoutes()
    }
}