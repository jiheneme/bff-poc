package com.example.domain.usecases

import com.example.domain.models.CardEntity
import com.example.domain.models.UserEntity
import com.example.domain.repository.CardRepository

class GetProfileUseCase(private val repository: CardRepository) {
    suspend fun execute(email: String): Pair<UserEntity, List<CardEntity>> {
        val user = repository.findUserByEmail(email)
        val cards = repository.findCardsByUserId(user.id)
        return Pair(user, cards)
    }
}