package com.example.presentation.routes

import com.example.domain.models.CardEntity
import com.example.domain.models.UserEntity
import com.example.domain.usecases.GetProfileUseCase
import com.example.presentation.responses.MobileProfileResponse
import com.example.presentation.controllers.ProfileController
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.install
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

class ProfileRoutesTest {

    private val useCaseMock = mockk<GetProfileUseCase>()

    @AfterTest
    fun tearDown() = stopKoin()

    @Test
    fun `test get profile route`() = testApplication {
        application {
            // Côté Serveur
            install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) { json() }
            install(Koin) {
                modules(module {
                    single { useCaseMock }
                    single { ProfileController(get()) }
                })
            }

            routing { profileRoutes() }
        }

        // Configuration du client pour la désérialisation
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) { json() }
        }

        val mockEmail = "test@example.com"
        coEvery { useCaseMock.execute(mockEmail) } returns Pair(
            UserEntity("1", "Franck", mockEmail),
            listOf(CardEntity("c1", "8888", "ACTIVE"))
        )

        val response = client.get("/mobile/profile") {
            parameter("email", mockEmail)
        }

        assertEquals(HttpStatusCode.OK, response.status)

        val body = response.body<MobileProfileResponse>()
        assertEquals("Franck", body.userName)
        assertEquals("Carte **** 8888", body.cards.first().label)
    }
}