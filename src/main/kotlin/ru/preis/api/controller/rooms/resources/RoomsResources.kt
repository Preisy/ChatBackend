package ru.preis.api.controller.rooms.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

enum class SortType(val str: String) {
    DATETIME_LESS("-datetime"),
    DATETIME_GREATER("+datetime")
}

@Serializable
@Resource("/rooms")
class Rooms {
    @Serializable
    @Resource("/new")
    class New(val parent: Rooms = Rooms())

    @Serializable
    @Resource("/{id}")
    class Id(val parent: Rooms = Rooms(), val id: Int) {

        @Serializable
        @Resource("/messages")
        class Messages(val parent: Id,
                       val sort: String = "-datetime",
                       val limit: Int? = null)

        @Serializable
        @Resource("/users")
        class Users(val parent: Id)
    }
}