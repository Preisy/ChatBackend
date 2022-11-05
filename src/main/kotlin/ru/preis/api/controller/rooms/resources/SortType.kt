package ru.preis.api.controller.rooms.resources

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SortType {
    @SerialName("-datetime")
    DATETIME_LESS,
    @SerialName("+datetime") // in uri should be %2Bdatetime
    DATETIME_GREATER
}