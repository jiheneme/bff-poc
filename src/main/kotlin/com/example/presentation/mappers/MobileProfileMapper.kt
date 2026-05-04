package com.example.presentation.mappers

import com.example.domain.models.CardEntity
import com.example.domain.models.UserEntity
import com.example.presentation.responses.MobileProfileResponse

fun toMobileProfileResponse(user: UserEntity, cards: List<CardEntity>): MobileProfileResponse {
    return MobileProfileResponse(
        userName = user.name,
        cards = cards.map { it.toMobileCardResponse() }
    )
}
