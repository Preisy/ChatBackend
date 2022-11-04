package ru.preis.database.repositories

import io.ktor.http.*
//import kotlinx.datetime.toKotlinLocalDateTime
//import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import ru.preis.database.model.*
import ru.preis.database.unitOfWork.DatabaseFactory
//import kotlinx.datetime.toKotlinLocalDateTime

class MessagesRepository : Repository<MessageDAO> {
    private fun resultRowToModel(resultRow: ResultRow): MessageDAO {
        return MessageDAO(
            id = resultRow[Messages.id],
            roomId = resultRow[Messages.roomId],
            memberId = resultRow[Messages.memberId],
            message = resultRow[Messages.message],
//            datetime = resultRow[Messages.datetime]
        )
    }

    override suspend fun find(predicate: (MessageDAO) -> Boolean): Iterable<MessageDAO> = DatabaseFactory.dbQuery {
        Messages.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(predicate: (MessageDAO) -> Boolean): MessageDAO? = DatabaseFactory.dbQuery {
        Messages.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(predicate: (MessageDAO) -> Boolean): MessageDAO? = DatabaseFactory.dbQuery {
        Rooms.selectAll()
            .firstOrNull { predicate(resultRowToModel(it)) }
            ?.run(::resultRowToModel)
    }

    override suspend fun add(el: MessageDAO) {
        Messages.insert {
            it[Messages.roomId] = el.roomId
            it[Messages.memberId] = el.memberId
            it[Messages.message] = el.message
        }
    }

}