package com.example.presentation.mappers

import com.example.domain.models.CardEntity
import com.example.presentation.responses.MobileCardResponse

fun CardEntity.toMobileCardResponse(): MobileCardResponse {
    return MobileCardResponse(
        id = this.id,
        label = "Carte **** ${this.lastFour}",
        // La carte peut être bloquée seulement si elle est active
        canBeBlocked = this.status == "ACTIVE"
    )
}