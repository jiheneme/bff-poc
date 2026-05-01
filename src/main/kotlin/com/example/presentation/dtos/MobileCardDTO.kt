package com.example.presentation.dtos

import kotlinx.serialization.Serializable

@Serializable
data class MobileCardDTO(
    val id: String,
    val label: String,
    val canBeBlocked: Boolean
)
