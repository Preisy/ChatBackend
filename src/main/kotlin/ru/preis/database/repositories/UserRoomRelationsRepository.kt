package ru.preis.database.repositories

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import ru.preis.database.model.UserRoomRelationDAO
import ru.preis.database.model.UserRoomRelations
import ru.preis.database.unitOfWork.DatabaseFactory
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery

class UserRoomRelationsRepository : Repository<UserRoomRelationDAO> {
    private fun resultRowToModel(resultRow: ResultRow): UserRoomRelationDAO {
        return UserRoomRelationDAO(
            roomId = resultRow[UserRoomRelations.roomId],
            userId = resultRow[UserRoomRelations.userId]
        )
    }

    override suspend fun find(predicate: (UserRoomRelationDAO) -> Boolean): Iterable<UserRoomRelationDAO> = dbQuery {
        UserRoomRelations.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(predicate: (UserRoomRelationDAO) -> Boolean): UserRoomRelationDAO? = dbQuery {
        UserRoomRelations.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(predicate: (UserRoomRelationDAO) -> Boolean): UserRoomRelationDAO? = dbQuery {
        UserRoomRelations.selectAll()
            .firstOrNull { predicate(resultRowToModel(it)) }
            ?.run(::resultRowToModel)
    }

    override suspend fun add(el: UserRoomRelationDAO) = DatabaseFactory.dbQueryUnit {
        UserRoomRelations.insert {
            it[roomId] = el.roomId
            it[userId] = el.userId
        }
    }

}