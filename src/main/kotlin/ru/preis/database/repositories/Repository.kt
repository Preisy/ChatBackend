package ru.preis.database.repositories

import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import ru.preis.api.model.Model

sealed interface Repository<T : Model> {
    suspend fun findAll(predicate: (SqlExpressionBuilder.() -> Op<Boolean>)? = null): Iterable<T>

    suspend fun findSingleOrNull(predicate: SqlExpressionBuilder.() -> Op<Boolean>): T?

    suspend fun findFirstOrNull(predicate: SqlExpressionBuilder.() -> Op<Boolean>): T?

    suspend fun add(el: T): T?
}