package com.example.plugins

import com.example.di.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.dsl.module

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(
            module { single { environment.config } },
            configModule,                             
            dataModule,
            domainModule,
            presentationModule
        )
    }
}