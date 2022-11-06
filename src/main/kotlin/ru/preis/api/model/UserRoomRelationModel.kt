package ru.preis.api.model

data class UserRoomRelationModel(
    val roomId: UInt,
    val userId: UInt
) : Model