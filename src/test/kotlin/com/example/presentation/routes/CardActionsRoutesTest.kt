package com.example.presentation.routes

import com.example.domain.usecases.BlockCardUseCase
import com.example.presentation.responses.MobileCardActionResponse
import com.example.plugins.configureStatusPages
import com.example.presentation.controllers.CardController
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CardActionsRoutesTest {

    private val blockUseCaseMock = mockk<BlockCardUseCase>()

    @AfterTest
    fun tearDown() = stopKoin()

    @Test
    fun `test post block card success`() = testApplication {
        application {
            install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) { json() }
            install(Koin) { modules(module {
                single { blockUseCaseMock }
                single { CardController(get()) }
            }) }
            routing { cardActionsRoutes() }
        }

        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) { json() }
        }

        val cardId = "card_123"
        coEvery { blockUseCaseMock.execute(cardId) } returns true

        val response = client.post("/mobile/cards/$cardId/block")

        assertEquals(HttpStatusCode.OK, response.status)

        val body = response.body<MobileCardActionResponse>()
        assertEquals(cardId, body.cardId)
        assertTrue(body.isSuccess)
        assertEquals("Your card has been successfully blocked.", body.message)
    }

    @Test
    fun `test post block card failure`() = testApplication {
        application {
            install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) { json() }
            install(Koin) { modules(module {
                single { blockUseCaseMock }
                single { CardController(get()) }
            }) }
            routing { cardActionsRoutes() }
        }

        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) { json() }
        }

        val cardId = "card_999"
        coEvery { blockUseCaseMock.execute(cardId) } returns false

        val response = client.post("/mobile/cards/$cardId/block")

        // On vérifie le code 422 UnprocessableEntity u'on a développé dans la Route
        assertEquals(HttpStatusCode.UnprocessableEntity, response.status)

        val body = response.body<MobileCardActionResponse>()
        assertEquals(false, body.isSuccess)
    }

    @Test
    fun `test status pages handles connection error`() = testApplication {
        application {
            configureStatusPages()
            install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) { json() }
            install(Koin) { modules(module {
                single { blockUseCaseMock }
                single { CardController(get()) }
            }) }
            routing { cardActionsRoutes() }
        }

        val client = createClient { install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) { json() } }

        // On simule une erreur réseau que le Repo lancerait normalement
        coEvery { blockUseCaseMock.execute(any()) } throws java.net.ConnectException("Service Down")

        val response = client.post("/mobile/cards/123/block")

        // Vérifie que StatusPages a transformé le crash en 503
        assertEquals(HttpStatusCode.ServiceUnavailable, response.status)
    }
}