package ru.preis.api.model

import com.typesafe.config.Optional
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class RoomDTO(
    val id: Int? = null,
    val adminId: Int
)