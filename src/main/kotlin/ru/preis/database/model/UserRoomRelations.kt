package ru.preis.database.model

import org.jetbrains.exposed.sql.Table

object UserRoomRelations : Table() {
    val roomId = integer("roomId").index() references Rooms.id
    val userId = integer("userId").index() references Users.id
    override val primaryKey = PrimaryKey(roomId, userId)
}

data class UserRoomRelationDAO(
    val roomId: Int,
    val userId: Int
) : DAOModel