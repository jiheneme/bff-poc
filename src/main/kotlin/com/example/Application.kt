package com.example

import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.CardRepositoryImplMock
import com.example.domain.usecases.GetProfileUseCase
import com.example.presentation.routes.cardActionsRoutes
import com.example.presentation.routes.profileRoutes
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ServerNegotiation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientNegotiation

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    println("URLs chargées : ${environment.config.property("services.user.url").getString()}")
    println("URLs chargées : ${environment.config.property("services.cards.url").getString()}")

    // Configuration JSON du serveur
    install(ServerNegotiation) {
        json()
    }

    // --- LECTURE DU FICHIER .CONF ---
    val userUrl = environment.config.property("services.user.url").getString().removeSuffix("/")
    val cardsUrl = environment.config.property("services.cards.url").getString().removeSuffix("/")

    // --- CONFIGURATION DU LOGGING ---
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/mobile") }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            "HTTP $status: $httpMethod ${call.request.uri}"
        }
    }

    // Client HTTP pour appeler les microservices
    val httpClient = HttpClient(CIO) {
        install(ClientNegotiation) {
            json()
        }
        install(io.ktor.client.plugins.logging.Logging) {
            level = io.ktor.client.plugins.logging.LogLevel.INFO
        }
    }

    // Initialisation des couches Domain et Data
   // val repository = CardRepositoryImpl(httpClient, userUrl, cardsUrl)
    val repository = CardRepositoryImplMock()
    val getProfileUseCase = GetProfileUseCase(repository)

    routing {
        profileRoutes(getProfileUseCase)
        cardActionsRoutes(repository)
    }

}
