package com.example

import io.ktor.server.config.*

class ServiceConfig(config: ApplicationConfig) {
    val userUrl = config.property("services.user.url").getString().removeSuffix("/")
    val cardsUrl = config.property("services.cards.url").getString().removeSuffix("/")
}