package com.example.data.repository

import com.example.data.remote.MSCardResponse
import com.example.data.remote.MSUserResponse
import com.example.domain.models.UserEntity
import com.example.domain.models.CardEntity
import com.example.domain.repository.CardRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.isSuccess

class CardRepositoryImpl(private val client: HttpClient) : CardRepository {
    override suspend fun findUserByEmail(email: String): UserEntity {
        val dto = client.get("http://ms-user/users") { parameter("email", email) }.body<MSUserResponse>()
        return UserEntity(dto.id, dto.firstName, dto.email)
    }

    override suspend fun findCardsByUserId(userId: String): List<CardEntity> {
        val dtos = client.get("http://ms-cards/list/$userId").body<List<MSCardResponse>>()
        return dtos.map { CardEntity(it.cardId, it.pan.takeLast(4), it.state) }
    }

    override suspend fun requestCardBlock(cardId: String): Boolean {
        return client.post("http://ms-cards/block/$cardId").status.isSuccess()
    }
}