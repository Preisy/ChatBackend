package ru.preis.api.view

import com.typesafe.config.Optional
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class MessageView(
    val id: UInt? = null,
    val roomId: UInt,
    @Optional
    var memberId: UInt? = null,
    @Optional
    val memberName: String? = null,
    val message: String,
    @Optional
    val datetime: LocalDateTime? = null
)

