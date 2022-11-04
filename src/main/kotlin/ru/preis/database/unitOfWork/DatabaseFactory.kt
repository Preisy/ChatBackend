package ru.preis.database.unitOfWork

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.util.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*
import ru.preis.database.model.*
//import kotlinx.datetime.LocalDateTime
//import kotlinx.datetime.toJavaLocalDateTime


object DatabaseFactory {
    fun init() {
        Database.connect(hikari())

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
//                it[datetime] = LocalDateTime.parse("2021-03-27T02:16:20").toJavaLocalDateTime()
            }
            Messages.insert {
                it[roomId] = 1
                it[memberId] = 2
                it[message] = "Hello from Adel!"
//                it[datetime] = LocalDateTime.parse("2021-03-27T02:16:20").toJavaLocalDateTime()
            } // todo сделать автоматическое добавление админа в комнату

            Rooms.insert {
                it[id] = 2
                it[adminId] = 2
            }
            UserRoomRelations.insert {
                it[roomId] = 1
                it[userId] = 2
            }
            Messages.insert {
                it[roomId] = 2
                it[memberId] = 2
                it[message] = "Hello from Adel 2!"
//                it[datetime] = LocalDateTime.parse("2021-03-27T02:16:20").toJavaLocalDateTime()
            }
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun <T> dbQueryUnit(block: suspend () -> Unit): Unit =
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
}