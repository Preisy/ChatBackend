package ru.preis.database.repositories

import org.jetbrains.exposed.sql.*
import ru.preis.api.model.UserModel
import ru.preis.database.model.Users
import ru.preis.database.unitOfWork.DatabaseFactory.dbQuery

class UsersRepository : Repository<UserModel> {
    private fun resultRowToModel(resultRow: ResultRow): UserModel {
        return UserModel(
            id = resultRow[Users.id],
            name = resultRow[Users.name],
            password = resultRow[Users.password]
        )
    }

    override suspend fun findAll(
        predicate: (SqlExpressionBuilder.() -> Op<Boolean>)?
    ): Iterable<UserModel> = dbQuery {
        (if (predicate != null)
            Users.select(predicate)
        else
            Users.selectAll()
                ).map { resultRowToModel(it) }
    }

    override suspend fun findSingleOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): UserModel? = dbQuery {
        Users.select(predicate)
            .map { resultRowToModel(it) }
            .singleOrNull()
    }

    override suspend fun findFirstOrNull(
        predicate: SqlExpressionBuilder.() -> Op<Boolean>
    ): UserModel? = dbQuery {
        Users.select(predicate)
            .singleOrNull()
            ?.run(::resultRowToModel)
    }

    override suspend fun add(el: UserModel) = dbQuery {
        val insertStatement = Users.insert {
            it[name] = el.name
            it[password] = el.password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToModel)
    }

}