package com.example.presentation.responses

import kotlinx.serialization.Serializable

@Serializable
data class MobileProfileResponse(
    val userName: String,
    val cards: List<MobileCardResponse>
)
