package com.example.domain.repository

import com.example.domain.models.CardEntity
import com.example.domain.models.UserEntity

interface CardRepository {
    suspend fun findUserByEmail(email: String): UserEntity
    suspend fun findCardsByUserId(userId: String): List<CardEntity>
    suspend fun requestCardBlock(cardId: String): Boolean
}