package ru.preis.database.model

import org.jetbrains.exposed.sql.Table

object Rooms : Table() {
    val id = integer("id").autoIncrement()
    val adminId = integer("memberId") references Users.id

    override val primaryKey = PrimaryKey(id)
}

data class RoomDAO(
    var adminId: Int
) : DAOModel {
    var id: Int? = null
        private set

    constructor(id: Int, adminId: Int) : this(adminId) {
        this.id = id
    }
}