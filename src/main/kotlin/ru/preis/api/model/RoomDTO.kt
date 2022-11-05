package ru.preis.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomDTO(
    val id: Int? = null,
    val adminId: Int
)