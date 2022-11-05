package ru.preis.database.unitOfWork

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.preis.database.model.Messages
import ru.preis.database.model.Rooms
import ru.preis.database.model.UserRoomRelations
import ru.preis.database.model.Users


object DatabaseFactory {
    fun init() {
        Database.connect(hikari())

        initInsertions()
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun dbQueryUnit(block: suspend () -> Unit): Unit =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    private fun initInsertions() {
        transaction {
            SchemaUtils.create(Users)
            Users.insert {
                it[id] = 1
                it[name] = "Sirgay"
                it[password] = "123".hashCode()
            }
            Users.insert {
                it[id] = 2
                it[name] = "Adel"
                it[password] = "123".hashCode()
            }

            SchemaUtils.create(Rooms)
            Rooms.insert {
                it[id] = 1
                it[adminId] = 1
            }

            SchemaUtils.create(UserRoomRelations)
            UserRoomRelations.insert {
                it[roomId] = 1
                it[userId] = 1
            }
            UserRoomRelations.insert {
                it[roomId] = 1
                it[userId] = 2
            }

            SchemaUtils.create(Messages)
            Messages.insert {
                it[roomId] = 1
                it[memberId] = 1
                it[message] = "Hello from Sirgay!"
                it[dateTime] = "2010-06-01T22:19:44".toLocalDateTime()
            }
            Messages.insert {
                it[roomId] = 1
                it[memberId] = 2
                it[message] = "Hello from Adel!"
                it[dateTime] = "2010-06-02T22:19:44".toLocalDateTime()
            }

            Rooms.insert {
                it[id] = 2
                it[adminId] = 2
            }
            UserRoomRelations.insert {
                it[roomId] = 2
                it[userId] = 2
            }
            Messages.insert {
                it[roomId] = 2
                it[memberId] = 2
                it[message] = "Hello from Adel 2!"
                it[dateTime] = "2010-06-03T22:19:44".toLocalDateTime()
            }
        }
    }
}