package ru.preis.api.controller.rooms.resources

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SortType {
    @SerialName("-datetime")
    DATETIME_LESS,
    @SerialName("+datetime") // in uri should be %2Bdatetime
    DATETIME_GREATER
}

@Serializable
@Resource("/rooms")
class Rooms {
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
            val offset: UInt = 0u,
            val limit: UInt? = null
        )

        @Serializable
        @Resource("/users")
        class Users(val parent: Id)
    }
}