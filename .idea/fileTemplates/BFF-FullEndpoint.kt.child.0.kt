package com.example.domain.usecases

import com.example.domain.repository.${NAME}Repository

class ${NAME}UseCase(private val repository: ${NAME}Repository) {
    suspend fun execute(userId: String) = repository.${NAME}(userId)
}