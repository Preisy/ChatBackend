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
        newSuspendedTransaction { block() }

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
                it[id] = 1u
                it[name] = "Sirgay"
                it[password] = "123".hashCode()
            }
            Users.insert {
                it[id] = 2u
                it[name] = "Adel"
                it[password] = "123".hashCode()
            }

            SchemaUtils.create(Rooms)
            Rooms.insert {
                it[id] = 1u
                it[adminId] = 1u
            }

            SchemaUtils.create(UserRoomRelations)
            UserRoomRelations.insert {
                it[roomId] = 1u
                it[userId] = 1u
            }
            UserRoomRelations.insert {
                it[roomId] = 1u
                it[userId] = 2u
            }

            SchemaUtils.create(Messages)
            Messages.insert {
                it[roomId] = 1u
                it[memberId] = 1u
                it[message] = "Hello from Sirgay!"
                it[dateTime] = "2010-06-01T22:19:44".toLocalDateTime()
            }
            Messages.insert {
                it[Messages.roomId] = 1u
                it[Messages.memberId] = 2u
                it[Messages.message] = "Hello from Adel!"
                it[Messages.dateTime] = "2010-06-02T22:19:44".toLocalDateTime()
            }

            Rooms.insert {
                it[id] = 2u
                it[adminId] = 2u
            }
            UserRoomRelations.insert {
                it[roomId] = 2u
                it[userId] = 2u
            }
            Messages.insert {
                it[roomId] = 2u
                it[memberId] = 2u
                it[message] = "Hello from Adel 2!"
                it[dateTime] = "2010-06-03T22:19:44".toLocalDateTime()
            }
        }
    }
}