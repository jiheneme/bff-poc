package com.example.data.remote.mappers

import com.example.data.remote.MSCardResponse
import com.example.data.remote.MSUserResponse
import com.example.domain.models.CardEntity
import com.example.domain.models.UserEntity

fun MSUserResponse.toDomain(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.firstName,
        email = this.email
    )
}

fun MSCardResponse.toDomain(): CardEntity {
    return CardEntity(
        id = this.cardId,
        lastFour = this.pan.takeLast(4),
        status = this.state
    )
}