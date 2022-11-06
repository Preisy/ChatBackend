package ru.preis.database.repositories

import org.jetbrains.exposed.sql.*
import ru.preis.api.model.RoomModel
import ru.preis.database.model.Rooms
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery

class RoomsRepository : Repository<RoomModel> {
    private fun resultRowToModel(resultRow: ResultRow): RoomModel {
        return RoomModel(
            id = resultRow[Rooms.id],
            adminId = resultRow[Rooms.adminId]
        )
    }

    override suspend fun findAll(
        predicate: (SqlExpressionBuilder.() -> Op<Boolean>)?
    ): Iterable<RoomModel> = dbQuery {
        (if (predicate != null)
            Rooms.select(predicate)
        else
            Rooms.selectAll()
                ).map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): RoomModel? = dbQuery {
        Rooms.select(predicate)
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): RoomModel? = dbQuery {
        Rooms.select(predicate)
            .singleOrNull()
            ?.run(::resultRowToModel)
    }

    override suspend fun add(el: RoomModel): RoomModel? = dbQuery {
        val insertStatement = Rooms.insert {
            it[adminId] = el.adminId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModel)
    }

}