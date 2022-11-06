package ru.preis.database.model

import org.jetbrains.exposed.sql.Table

@OptIn(ExperimentalUnsignedTypes::class)
object Rooms : Table() {
    val id = uinteger("id").autoIncrement()
    val adminId = uinteger("memberId") references Users.id

    override val primaryKey = PrimaryKey(id)
}
