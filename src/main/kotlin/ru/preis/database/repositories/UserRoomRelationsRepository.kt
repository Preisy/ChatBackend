package ru.preis.database.repositories

import org.jetbrains.exposed.sql.*
import ru.preis.api.model.UserRoomRelationModel
import ru.preis.database.model.UserRoomRelations
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery

class UserRoomRelationsRepository : Repository<UserRoomRelationModel> {
    private fun resultRowToModel(resultRow: ResultRow): UserRoomRelationModel {
        return UserRoomRelationModel(
            roomId = resultRow[UserRoomRelations.roomId],
            userId = resultRow[UserRoomRelations.userId]
        )
    }

    override suspend fun findAll(
        predicate: (SqlExpressionBuilder.() -> Op<Boolean>)?
    ): Iterable<UserRoomRelationModel> = dbQuery {
        (if (predicate != null)
            UserRoomRelations.select(predicate)
        else
            UserRoomRelations.selectAll()
                ).map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): UserRoomRelationModel? = dbQuery {
        UserRoomRelations.select(predicate)
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): UserRoomRelationModel? = dbQuery {
        UserRoomRelations.select(predicate)
            .singleOrNull()
            ?.run(::resultRowToModel)
    }

    override suspend fun add(el: UserRoomRelationModel): UserRoomRelationModel? = dbQuery {
        val insertStatement = UserRoomRelations.insert {
            it[roomId] = el.roomId
            it[userId] = el.userId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModel)
    }

}