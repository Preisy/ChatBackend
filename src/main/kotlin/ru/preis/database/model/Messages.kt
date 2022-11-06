package ru.preis.database.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


@OptIn(ExperimentalUnsignedTypes::class)
object Messages : Table() {
    val id = uinteger("id").autoIncrement()
    val roomId = uinteger("roomId") references Rooms.id
    val memberId = uinteger("memberId") references Users.id
    val message = varchar("message", 255)
    val dateTime = datetime("datetime").clientDefault { Clock.System.now().toLocalDateTime(TimeZone.UTC) }

    override val primaryKey = PrimaryKey(id)
}