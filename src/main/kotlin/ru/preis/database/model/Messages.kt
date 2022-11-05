package ru.preis.database.model

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


@OptIn(ExperimentalUnsignedTypes::class)
object Messages : Table() {
    val id = uinteger("id").autoIncrement()
    val roomId = uinteger("roomId") references Rooms.id
    val memberId = uinteger("memberId") references Users.id
    val message = varchar("message", 255)
    val dateTime = datetime("datetime").defaultExpression(CurrentDateTime())

    override val primaryKey = PrimaryKey(id)
}

data class MessageDAO(
    val id: UInt? = null,
    val roomId: UInt,
    val memberId: UInt,
    val message: String,
    val datetime: LocalDateTime? = null
) : DAOModel