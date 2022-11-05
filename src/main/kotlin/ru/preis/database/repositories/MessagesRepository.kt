package ru.preis.database.repositories

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import ru.preis.database.model.MessageDAO
import ru.preis.database.model.Messages
import ru.preis.database.model.Rooms
import ru.preis.database.unitOfWork.DatabaseFactory
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery

class MessagesRepository : Repository<MessageDAO> {
    private fun resultRowToModel(resultRow: ResultRow): MessageDAO {
        return MessageDAO(
            id = resultRow[Messages.id],
            roomId = resultRow[Messages.roomId],
            memberId = resultRow[Messages.memberId],
            message = resultRow[Messages.message],
            datetime = resultRow[Messages.dateTime]
        )
    }

    override suspend fun find(predicate: (MessageDAO) -> Boolean): Iterable<MessageDAO> = dbQuery {
        Messages.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(predicate: (MessageDAO) -> Boolean): MessageDAO? = dbQuery {
        Messages.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(predicate: (MessageDAO) -> Boolean): MessageDAO? = dbQuery {
        Rooms.selectAll()
            .firstOrNull { predicate(resultRowToModel(it)) }
            ?.run(::resultRowToModel)
    }

    override suspend fun add(el: MessageDAO): MessageDAO? = dbQuery {
        val insertStatement = Messages.insert {
            it[roomId] = el.roomId
            it[memberId] = el.memberId
            it[message] = el.message
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModel)
    }

}