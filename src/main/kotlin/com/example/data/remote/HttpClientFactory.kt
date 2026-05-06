package com.example.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object HttpClientFactory {
    fun create(): HttpClient = HttpClient(CIO) {

        install(HttpTimeout) {
            requestTimeoutMillis = 5000 // 5 secondes
            connectTimeoutMillis = 2000
        }
        // Headers management
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            // On pourrait ajouter ici : header("X-Source", "BFF-Mobile")
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Évite le crash si le microservice ajoute des champs
                isLenient = true
            })
        }
    }
}