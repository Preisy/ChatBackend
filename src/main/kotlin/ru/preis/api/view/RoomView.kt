package ru.preis.api.view

import kotlinx.serialization.Serializable

@Serializable
data class RoomView(
    val id: UInt? = null,
    val adminId: UInt
)