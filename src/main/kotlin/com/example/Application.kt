package com.example

import com.example.plugins.*
import com.example.presentation.routes.cardActionsRoutes
import com.example.presentation.routes.profileRoutes
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.dsl.module

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {

    install(Koin) {
        slf4jLogger()
        // On injecte la config Ktor pour que ServiceConfig puisse la lire dans AppModule
        modules(module { single { environment.config } }, appModule)
    }

    configureSerialization()
    configureMonitoring()

    routing {
        profileRoutes()
        cardActionsRoutes()
    }
}