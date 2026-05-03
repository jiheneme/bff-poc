package com.example.presentation.routes

import com.example.domain.usecases.GetProfileUseCase
import com.example.domain.models.UserEntity
import com.example.domain.models.CardEntity
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.*
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.ktor.plugin.Koin // Import pour koin-ktor 3.6.0
import org.koin.dsl.module
import io.ktor.server.routing.* // Import indispensable pour l'extension .routing
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProfileRoutesTest {

    private val useCaseMock = mockk<GetProfileUseCase>()

    @Test
    fun `test get profile route`() = testApplication {
        application {
            this.install(ContentNegotiation) {
                json()
            }
            this.install(Koin) {
                modules(module {
                    single { useCaseMock }
                })
            }
            this.routing {
                profileRoutes()
            }
        }

        val mockEmail = "test@example.com"
        coEvery { useCaseMock.execute(mockEmail) } returns Pair(
            UserEntity("1", "Franck", mockEmail),
            listOf(CardEntity("c1", "8888", "ACTIVE"))
        )

        // Appel via le client HTTP simulé
        val response = client.get("/mobile/profile") {
            parameter("email", mockEmail)
        }

        assertEquals(HttpStatusCode.OK, response.status)

        val body = response.bodyAsText()
        assertTrue(body.contains("Franck"), "Le nom devrait être présent")
        assertTrue(body.contains("8888"), "Les numéros de carte devraient être présents")
    }
}