package ru.preis.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class MessageDTO(
    val id: Int? = null,
    val roomId: Int,
    val memberId: Int,
    val message: String,
    val datetime: LocalDateTime? = null
)

