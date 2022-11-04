package ru.preis.database.repositories

import ru.preis.database.model.DAOModel
import kotlin.reflect.KProperty1

sealed interface Repository<T : DAOModel> {
    suspend fun find(predicate: (T) -> Boolean): Iterable<T>

    suspend fun findSingleOrNull(predicate: (T) -> Boolean): T?

    suspend fun findFirstOrNull(predicate: (T) -> Boolean): T?

    suspend fun add(el: T)
}