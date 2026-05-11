package com.example.plugins
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.http.*
import io.ktor.http.content.CachingOptions
import io.ktor.http.CacheControl
import io.ktor.server.request.uri

fun Application.configureCaching() {
    install(CachingHeaders) {
        options { call, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Application.Json -> {
                    // On cache les profils, mais pas les actions de blocage
                    if (call.request.uri.contains("/mobile/profile")) {
                        CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 60))
                    } else null
                }
                else -> null
            }
        }
    }
}