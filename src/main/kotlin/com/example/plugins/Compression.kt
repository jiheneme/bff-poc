package com.example.plugins
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.compression.*

fun Application.configureCompression() {
    install(Compression) {
        gzip {
            priority = 1.0
            minimumSize(1024) // Ne compresse pas si le JSON fait moins de 1Ko
        }
    }
}