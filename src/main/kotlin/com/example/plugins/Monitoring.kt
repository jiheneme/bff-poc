package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import org.slf4j.event.Level
import org.slf4j.MDC
import java.util.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
       // filter { call -> call.request.path().startsWith("/mobile") }

        mdc("traceId") { call ->
            call.request.header("X-Trace-Id") ?: UUID.randomUUID().toString()
        }
    }
}