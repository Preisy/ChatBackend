package ru.preis.database.repositories

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import ru.preis.database.model.UserDAO
import ru.preis.database.model.Users
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery
import ru.preis.database.unitOfWork.DatabaseFactory.dbQueryUnit

class UsersRepository : Repository<UserDAO> {
    private fun resultRowToModel(resultRow: ResultRow): UserDAO {
        return UserDAO(
            id = resultRow[Users.id],
            name = resultRow[Users.name],
            password = resultRow[Users.password]
        )
    }

    override suspend fun find(predicate: (UserDAO) -> Boolean): Iterable<UserDAO> = dbQuery {
        Users.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
    }

    override suspend fun findFirstOrNull(predicate: (UserDAO) -> Boolean): UserDAO? = dbQuery {
        Users.selectAll()
            .firstOrNull { predicate(resultRowToModel(it)) }
            ?.run(::resultRowToModel)
    }

    override suspend fun findSingleOrNull(predicate: (UserDAO) -> Boolean): UserDAO? = dbQuery {
        Users.selectAll()
            .filter { predicate(resultRowToModel(it)) }
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun add(el: UserDAO) = dbQueryUnit {
        Users.insert {
            it[name] = el.name
            it[password] = el.password
        }
    }

}