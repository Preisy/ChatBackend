package ru.preis.api.controller.rooms.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable


@Serializable
@Resource("/rooms")
class Rooms(
    val offset: UInt? = 0u,
    val limit: UInt? = null
) {
    @Serializable
    @Resource("/new")
    class New(val parent: Rooms = Rooms())

    @Serializable
    @Resource("/{id}")
    class Id(val parent: Rooms = Rooms(), val id: UInt) {

        @Serializable
        @Resource("/messages")
        class Messages(
            val parent: Id,
            val sort: SortType = SortType.DATETIME_GREATER,
            val offset: UInt? = 0u,
            val limit: UInt? = null
        )

        @Serializable
        @Resource("/users")
        class Users(val parent: Id)
    }
}