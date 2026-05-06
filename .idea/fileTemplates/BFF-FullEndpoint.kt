package com.example.presentation.controllers

import com.example.domain.usecases.${NAME}UseCase
import io.ktor.server.application.*
import io.ktor.server.response.*

class ${NAME}Controller(private val ${NAME}UseCase: ${NAME}UseCase) {
    suspend fun ${NAME}(call: ApplicationCall) {
        val userId = call.parameters["userId"] ?: return call.respond(io.ktor.http.HttpStatusCode.BadRequest)
        val result = ${NAME}UseCase.execute(userId)
        call.respond(result)
    }
}