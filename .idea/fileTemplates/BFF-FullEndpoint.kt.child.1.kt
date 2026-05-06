package com.example.domain.repository

import com.example.domain.models.${NAME}Entity

interface ${NAME}Repository {
    suspend fun ${NAME}(userId: String): List<${NAME}Entity>
}