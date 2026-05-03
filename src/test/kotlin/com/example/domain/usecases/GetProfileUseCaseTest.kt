package com.example.domain.usecases

import com.example.domain.models.CardEntity
import com.example.domain.models.UserEntity
import com.example.domain.repository.CardRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class GetProfileUseCaseTest {

    // On mocke l'interface du repository
    private val repository = mockk<CardRepository>()
    private val useCase = GetProfileUseCase(repository)

    @Test
    fun `execute should return aggregated user and cards when repository returns data`() = runBlocking {
        // GIVEN
        val email = "franck@gmail.com"
        val mockUser = UserEntity("user_1", "Franck", email)
        val mockCards = listOf(
            CardEntity("id_1", "4242", "ACTIVE"),
            CardEntity("id_2", "1234", "BLOCKED")
        )

        // On définit le comportement du mock (coEvery pour les fonctions suspend)
        coEvery { repository.findUserByEmail(email) } returns mockUser
        coEvery { repository.findCardsByUserId("user_1") } returns mockCards

        // WHEN (On execute l'action)
        val result = useCase.execute(email)

        // THEN (On vérifie le résultat)
        assertEquals("Franck", result.first.name)
        assertEquals(2, result.second.size)
        assertEquals("4242", result.second[0].lastFour)
    }
}