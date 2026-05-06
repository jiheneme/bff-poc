package com.example.data.repository

import com.example.data.remote.MSCardResponse
import com.example.data.remote.MSUserResponse
import com.example.data.remote.mappers.toDomain
import com.example.domain.models.UserEntity
import com.example.domain.models.CardEntity
import com.example.domain.repository.CardRepository
import com.example.domain.exceptions.UserNotFoundException
import com.example.domain.exceptions.RemoteServiceException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.plugins.*
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

class CardRepositoryImpl(
    private val client: HttpClient,
    private val userBaseUrl: String,
    private val cardsBaseUrl: String
) : CardRepository {

    override suspend fun findUserByEmail(email: String): UserEntity {
        val response = client.get("$userBaseUrl/users") { parameter("email", email) }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<MSUserResponse>().toDomain()
            HttpStatusCode.NotFound -> throw UserNotFoundException(email)
            else -> throw RemoteServiceException("User-Service", response.status.value)
        }
    }
    override suspend fun findCardsByUserId(userId: String): List<CardEntity> {
        val response = client.get("$cardsBaseUrl/list/$userId")

        return if (response.status.isSuccess()) {
            response.body<List<MSCardResponse>>().map { it.toDomain() }
        } else {
            // On peut décider de renvoyer une liste vide ou de lever une erreur
            emptyList()
        }
    }
    override suspend fun requestCardBlock(cardId: String): Boolean {
        val response = client.post("$cardsBaseUrl/block/$cardId") {
            timeout {
                requestTimeoutMillis = 2000 // 2 secondes au lieu de 5
            }
        }
        return response.status.isSuccess()
    }
}
