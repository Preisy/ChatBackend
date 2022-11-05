package ru.preis.api.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class UserDTO(
    val id: UInt? = null,
    val name: String,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val password: String? = null
)