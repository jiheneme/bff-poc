package com.example.data.repository

import com.example.domain.repository.${NAME}Repository
import com.example.domain.models.${NAME}Entity
import io.ktor.client.*

class ${NAME}RepositoryImpl(private val client: HttpClient, private val baseUrl: String) : ${NAME}Repository {
    override suspend fun get${NAME}s(userId: String): List<${NAME}Entity> {
        // TODO: Implémenter l'appel au Microservice
        return emptyList()
    }
}