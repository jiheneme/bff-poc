package com.example

import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.CardRepositoryImplMock
import com.example.domain.repository.CardRepository
import com.example.domain.usecases.GetProfileUseCase
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import org.koin.dsl.module

val appModule = module {

    single { ServiceConfig(get<ApplicationConfig>()) }

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        }
    }

    single<CardRepository> {
        val config = get<ServiceConfig>()
        CardRepositoryImpl(// CardRepositoryImplMock to test
            client = get(),
            userBaseUrl = config.userUrl,
            cardsBaseUrl = config.cardsUrl
        )
    }

    single { GetProfileUseCase(get()) }
}