package ru.preis.database.model

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255).uniqueIndex()
    val password = integer("password")
    override val primaryKey = PrimaryKey(id)
}


data class UserDAO(
    val name: String,
    val password: Int
) : DAOModel {
    var id: Int? = null
        private set

    constructor(id: Int, name: String, password: Int) : this(name, password) {
        this.id = id
    }
}