package com.example.presentation.dtos.responses

import com.example.presentation.responses.MobileCardResponse
import kotlinx.serialization.Serializable

@Serializable
data class MobileProfileResponse(
    val userName: String,
    val cards: List<MobileCardResponse>
)
