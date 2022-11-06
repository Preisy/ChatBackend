package ru.preis.api.model

data class UserModel(
    val id: UInt? = null,
    val name: String,
    val password: Int
) : Model