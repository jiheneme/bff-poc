package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.*
import org.slf4j.event.Level
import java.util.*

fun Application.configureMonitoring() {
    install(CallLogging) {


        level = Level.INFO

        mdc("traceId") { call ->
            call.request.header("X-Trace-Id")
                ?: UUID.randomUUID().toString()
        }
    }
}