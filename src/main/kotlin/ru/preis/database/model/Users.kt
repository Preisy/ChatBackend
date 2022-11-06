package ru.preis.database.model

import org.jetbrains.exposed.sql.Table

@OptIn(ExperimentalUnsignedTypes::class)
object Users : Table() {
    val id = uinteger("id").autoIncrement()
    val name = varchar("name", 255).uniqueIndex()
    val password = integer("password")
    override val primaryKey = PrimaryKey(id)
}
