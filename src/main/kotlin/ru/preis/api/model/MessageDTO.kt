package ru.preis.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class MessageDTO(
    val id: UInt? = null,
    val roomId: UInt,
    val memberId: UInt,
    val message: String,
    val datetime: LocalDateTime? = null
)

