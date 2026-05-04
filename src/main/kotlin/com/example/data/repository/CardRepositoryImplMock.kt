package com.example.data.repository

import com.example.data.remote.MSCardResponse
import com.example.data.remote.MSUserResponse
import com.example.data.remote.mappers.toDomain
import com.example.domain.models.CardEntity
import com.example.domain.models.UserEntity
import com.example.domain.repository.CardRepository
import kotlinx.serialization.json.Json
import java.io.InputStream

class CardRepositoryImplMock : CardRepository {

    private val jsonParser = Json { ignoreUnknownKeys = true }

    override suspend fun findUserByEmail(email: String): UserEntity {
        val dto = loadMock<MSUserResponse>("mocks/user_mock.json")
        return dto.toDomain()
    }

    override suspend fun findCardsByUserId(userId: String): List<CardEntity> {
        // Simulation de DTOs MSCardResponse
        val dtos = listOf(
            MSCardResponse(cardId = "card_888", pan = "4242424242424242", state = "ACTIVE"),
            MSCardResponse(cardId = "card_999", pan = "1234123412341234", state = "BLOCKED")
        )

        // Utilisation du mapper sur chaque élément de la liste
        return dtos.map { it.toDomain() }
    }

    override suspend fun requestCardBlock(cardId: String): Boolean {
        return true // succès
    }

    private inline fun <reified T> loadMock(fileName: String): T {
        val inputStream: InputStream = this::class.java.classLoader.getResourceAsStream(fileName)
            ?: throw Exception("Mock file not found in resources: $fileName")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return jsonParser.decodeFromString(jsonString)
    }
}