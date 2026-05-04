package com.example.domain.usecases

import com.example.domain.repository.CardRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class BlockCardUseCaseTest {

    private val repository = mockk<CardRepository>()
    private val useCase = BlockCardUseCase(repository)

    @Test
    fun `execute should return true when repository successfully blocks the card`() = runBlocking {
        // GIVEN
        val cardId = "card_888"
        coEvery { repository.requestCardBlock(cardId) } returns true

        // WHEN
        val result = useCase.execute(cardId)

        // THEN
        assertEquals(true, result)
    }

    @Test
    fun `execute should return false when repository fails to block the card`() = runBlocking {
        // GIVEN
        val cardId = "card_999"
        coEvery { repository.requestCardBlock(cardId) } returns false

        // WHEN
        val result = useCase.execute(cardId)

        // THEN
        assertEquals(false, result)
    }
}