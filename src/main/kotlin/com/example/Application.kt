package com.example

import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.CardRepositoryImplMock
import com.example.domain.usecases.GetProfileUseCase
import com.example.presentation.routes.cardActionsRoutes
import com.example.presentation.routes.profileRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ServerNegotiation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.uri
import org.slf4j.event.Level
import io.ktor.client.plugins.logging.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        // Configuration JSON
        install(ServerNegotiation) { json() }

        // --- AJOUT DU LOGGING ---
        install(CallLogging) {
            level = Level.INFO // Niveau de détail (INFO est parfait pour le dev)

            // Optionnel : Filtrer pour ne loguer que les appels /mobile
            filter { call -> call.request.path().startsWith("/mobile") }

            // Optionnel : Personnaliser le format du log
            format { call ->
                val status = call.response.status()
                val httpMethod = call.request.httpMethod.value
                val userAgent = call.request.headers["User-Agent"]
                "HTTP $status: $httpMethod ${call.request.uri} [Agent: $userAgent]"
            }
        }

        // Client HTTP pour les microservices
        val httpClient = HttpClient(CIO) {
            install(io.ktor.client.plugins.logging.Logging) {
                level = io.ktor.client.plugins.logging.LogLevel.BODY
            }
            install(ClientNegotiation) { json() }
        }

        // Injection de dépendances (Manuelle pour le PoC) add Koin / modules.kt later
        //val repository = CardRepositoryImpl(httpClient)
        val repository = CardRepositoryImplMock()
        val getProfileUseCase = GetProfileUseCase(repository)

        // Enregistrement des fichiers de routes séparés
        routing {
            profileRoutes(getProfileUseCase)
            cardActionsRoutes(repository)
        }
    }.start(wait = true)
}