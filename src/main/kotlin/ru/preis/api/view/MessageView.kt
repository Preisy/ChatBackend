package ru.preis.api.view

import com.typesafe.config.Optional
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class MessageView(
    val id: UInt? = null,
    val roomId: UInt,
    val memberId: UInt,
    val message: String,
    @Optional
    val datetime: LocalDateTime? = null
)

