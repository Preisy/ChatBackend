package ru.preis.database.model

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


object Messages : Table() {
    val id = integer("id").autoIncrement()
    val roomId = integer("roomId") references Rooms.id
    val memberId = integer("memberId") references Users.id
    val message = varchar("message", 255)
    val dateTime = datetime("datetime").defaultExpression(CurrentDateTime())

    override val primaryKey = PrimaryKey(id)
}

data class MessageDAO(
    val id: Int? = null,
    val roomId: Int,
    val memberId: Int,
    val message: String,
    val datetime: LocalDateTime? = null
) : DAOModel