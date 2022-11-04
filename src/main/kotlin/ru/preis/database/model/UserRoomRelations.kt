package ru.preis.database.model

import org.jetbrains.exposed.sql.Table

object UserRoomRelations : Table() {
    val roomId = integer("roomId").index() references Rooms.id
    val userId = integer("userId").index() references Users.id
}

data class UserRoomRelationDAO(
    val roomId: Int,
    val userId: Int
) : DAOModel