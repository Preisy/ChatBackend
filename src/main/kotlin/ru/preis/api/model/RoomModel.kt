package ru.preis.api.model

data class RoomModel(
    val id: UInt? = null,
    var adminId: UInt
) : Model