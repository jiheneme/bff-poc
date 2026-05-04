package com.example.domain.usecases

import com.example.domain.repository.CardRepository

class BlockCardUseCase(private val repository: CardRepository) {
    suspend fun execute(cardId: String): Boolean {
        // Ici, on pourrait ajouter une règle métier :
        // ex vérifier si l'utilisateur a le droit de bloquer cette carte
        return repository.requestCardBlock(cardId)
    }
}