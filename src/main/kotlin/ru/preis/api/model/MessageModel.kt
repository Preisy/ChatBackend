package ru.preis.api.model

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.Entity

data class MessageModel(
    val id: UInt? = null,
    val roomId: UInt,
    val memberId: UInt,
    val message: String,
    val datetime: LocalDateTime? = null
) : Model
