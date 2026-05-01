package com.example.domain.models

data class UserEntity(val id: String, val name: String, val email: String)
data class CardEntity(val id: String, val lastFour: String, val status: String)