package ru.preis.database.repositories

import org.jetbrains.exposed.sql.*
import ru.preis.api.model.MessageModel
import ru.preis.database.model.Messages
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery

class MessagesRepository : Repository<MessageModel> {
    private fun resultRowToModel(resultRow: ResultRow): MessageModel {
        return MessageModel(
            id = resultRow[Messages.id],
            roomId = resultRow[Messages.roomId],
            memberId = resultRow[Messages.memberId],
            message = resultRow[Messages.message],
            datetime = resultRow[Messages.dateTime]
        )
    }

    override suspend fun findAll(
        predicate: (SqlExpressionBuilder.() -> Op<Boolean>)?
    ): Iterable<MessageModel> = dbQuery {
        (if (predicate != null)
            Messages.select(predicate)
        else
            Messages.selectAll()
                ).map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): MessageModel? = dbQuery {
        Messages.select(predicate)
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): MessageModel? = dbQuery {
        Messages.select(predicate)
            .singleOrNull()
            ?.run(::resultRowToModel)
    }

    override suspend fun add(el: MessageModel): MessageModel? = dbQuery {
        val insertStatement = Messages.insert {
            it[roomId] = el.roomId
            it[memberId] = el.memberId
            it[message] = el.message
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModel)
    }

}