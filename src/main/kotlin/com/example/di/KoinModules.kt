package com.example.di

import com.example.config.ServiceConfig
import com.example.data.remote.HttpClientFactory
import com.example.data.repository.CardRepositoryImpl
import com.example.data.repository.CardRepositoryImplMock
import com.example.domain.repository.CardRepository
import com.example.domain.usecases.*
import com.example.presentation.controllers.*
import org.koin.dsl.module

// --- DATA ---
val dataModule = module {
    single { HttpClientFactory.create() }

    single<CardRepository> {
        val config = get<ServiceConfig>()
       /* CardRepositoryImpl(
            client = get(),
            userBaseUrl = config.userUrl,
            cardsBaseUrl = config.cardsUrl
        )*/
        CardRepositoryImplMock()
    }
}
// --- DOMAIN ---
val domainModule = module {
    single { GetProfileUseCase(get()) }
    single { BlockCardUseCase(get()) }
}

// --- PRESENTATION ---
val presentationModule = module {
    single { ProfileController(get()) }
    single { CardController(get()) }
}

// --- CONFIGURATION ---
val configModule = module {
    single { ServiceConfig(get()) }
}