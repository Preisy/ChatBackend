package ru.preis.api.model

import kotlinx.serialization.Serializable
//import kotlinx.datetime.LocalDateTime


@Serializable
data class MessageDTO(
    val id: Int? = null,
    val roomId: Int,
    val memberId: Int,
    val message: String,
//    val datetime: LocalDateTime? = null
)

