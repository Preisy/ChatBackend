package ru.preis.database.model

import org.jetbrains.exposed.sql.Table

@OptIn(ExperimentalUnsignedTypes::class)
object UserRoomRelations : Table() {
    val roomId = uinteger("roomId").index() references Rooms.id
    val userId = uinteger("userId").index() references Users.id
    override val primaryKey = PrimaryKey(roomId, userId)
}
