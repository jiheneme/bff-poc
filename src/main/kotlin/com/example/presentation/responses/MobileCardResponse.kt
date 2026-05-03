package com.example.presentation.responses

import kotlinx.serialization.Serializable

@Serializable
data class MobileCardResponse(
    val id: String,
    val label: String,
    val canBeBlocked: Boolean
)