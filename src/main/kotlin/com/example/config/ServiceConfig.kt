package com.example.config

import io.ktor.server.config.ApplicationConfig

class ServiceConfig(config: ApplicationConfig) {
    val userUrl = config.property("services.user.url").getString().removeSuffix("/")
    val cardsUrl = config.property("services.cards.url").getString().removeSuffix("/")
}