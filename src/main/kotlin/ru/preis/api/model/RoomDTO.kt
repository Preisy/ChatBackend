package ru.preis.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomDTO(
    val id: UInt? = null,
    val adminId: UInt
)