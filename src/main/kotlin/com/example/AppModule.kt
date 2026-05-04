package com.example

import com.example.data.remote.HttpClientFactory
import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.CardRepositoryImplMock
import com.example.domain.repository.CardRepository
import com.example.domain.usecases.GetProfileUseCase
import com.example.domain.usecases.BlockCardUseCase
import io.ktor.server.config.*
import org.koin.dsl.module

val appModule = module {

    single { ServiceConfig(get<ApplicationConfig>()) }

    // Utilisation du Networker (HttpClientFactory)
    single { HttpClientFactory.create() }

    single<CardRepository> {
        val config = get<ServiceConfig>()

        // Pour utiliser le Mock durant les tests, remplace CardRepositoryImpl par CardRepositoryImplMock()
        CardRepositoryImpl(
            client = get(),
            userBaseUrl = config.userUrl,
            cardsBaseUrl = config.cardsUrl
        )
    }

    // Use Cases
    single { GetProfileUseCase(get()) }
    single { BlockCardUseCase(get()) }
}