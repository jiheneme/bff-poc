package com.example.presentation.mappers

import com.example.presentation.responses.MobileCardActionResponse

fun toCardActionResponse(cardId: String, isSuccess: Boolean): MobileCardActionResponse {
    return if (isSuccess) {
        MobileCardActionResponse(
            cardId = cardId,
            isSuccess = true,
            message = "Your card has been successfully blocked."
        )
    } else {
        MobileCardActionResponse(
            cardId = cardId,
            isSuccess = false,
            message = "Unable to block the card. Please try again later."
        )
    }
}