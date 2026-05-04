package com.example.data.repository

import com.example.data.remote.MSCardResponse
import com.example.data.remote.MSUserResponse
import com.example.data.remote.mappers.toDomain
import com.example.domain.models.UserEntity
import com.example.domain.models.CardEntity
import com.example.domain.repository.CardRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.isSuccess

class CardRepositoryImpl(
    private val client: HttpClient,
    private val userBaseUrl: String,
    private val cardsBaseUrl: String
) : CardRepository {

    override suspend fun findUserByEmail(email: String): UserEntity {
        return client.get("$userBaseUrl/users") {
            parameter("email", email)
        }.body<MSUserResponse>().toDomain()
    }

    override suspend fun findCardsByUserId(userId: String): List<CardEntity> {
        val dtos = client.get("$cardsBaseUrl/list/$userId").body<List<MSCardResponse>>()
        return dtos.map { it.toDomain() }
    }

    override suspend fun requestCardBlock(cardId: String): Boolean {
        return client.post("$cardsBaseUrl/block/$cardId").status.isSuccess()
    }
}
