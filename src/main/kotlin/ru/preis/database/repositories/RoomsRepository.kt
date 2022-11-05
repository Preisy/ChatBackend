package ru.preis.database.repositories

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import ru.preis.database.model.RoomDAO
import ru.preis.database.model.Rooms
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery
import ru.preis.database.unitOfWork.DatabaseFactory.dbQueryUnit

class RoomsRepository : Repository<RoomDAO> {
    private fun resultRowToModel(resultRow: ResultRow): RoomDAO {
        return RoomDAO(
            id = resultRow[Rooms.id],
            adminId = resultRow[Rooms.adminId]
        )
    }

    override suspend fun find(predicate: (RoomDAO) -> Boolean): Iterable<RoomDAO> = dbQuery {
        Rooms.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(predicate: (RoomDAO) -> Boolean): RoomDAO? = dbQuery {
        Rooms.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(predicate: (RoomDAO) -> Boolean): RoomDAO? = dbQuery {
        Rooms.selectAll()
            .firstOrNull { predicate(resultRowToModel(it)) }
            ?.run(::resultRowToModel)
    }


    override suspend fun add(el: RoomDAO) = dbQueryUnit {
        Rooms.insert {
            it[adminId] = el.adminId
        }
    }

}