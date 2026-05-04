package com.example.presentation.responses

import kotlinx.serialization.Serializable

@Serializable
data class MobileCardActionResponse(
    val cardId: String,
    val isSuccess: Boolean,
    val message: String
)