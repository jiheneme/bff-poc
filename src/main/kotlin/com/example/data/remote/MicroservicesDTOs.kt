package com.example.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class MSUserResponse(val id: String, val firstName: String, val email: String)
@Serializable
data class MSCardResponse(val cardId: String, val pan: String, val state: String)
