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
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProfileRoutesTest {

    // Mock du UseCase pour isoler le test de la couche Domain/Data
    private val useCase = mockk<GetProfileUseCase>()

    @Test
    fun `test get profile route`() = testApplication {
        application {
            this.install(ContentNegotiation) {
                json()
            }
            this.routing {
                profileRoutes(useCase)
            }
        }

        // Mocking de la réponse du UseCase
        val mockEmail = "test@example.com"
        coEvery { useCase.execute(mockEmail) } returns Pair(
            UserEntity("1", "Franck", mockEmail),
            listOf(CardEntity("c1", "8888", "ACTIVE"))
        )

        // Exécution de l'appel HTTP via le client de test
        val response = client.get("/mobile/profile") {
            parameter("email", mockEmail)
        }

        assertEquals(HttpStatusCode.OK, response.status)

        // Vérification du contenu JSON pour s'assurer que le mapping BFF est correct
        val body = response.bodyAsText()
        assertTrue(body.contains("Franck"), "Le JSON devrait contenir le nom d'utilisateur")
        assertTrue(body.contains("8888"), "Le JSON devrait contenir les 4 derniers chiffres de la carte")
        assertTrue(body.contains("Carte **** 8888"), "Le label formatté devrait être présent")
    }
}