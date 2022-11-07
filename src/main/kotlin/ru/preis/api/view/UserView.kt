package ru.preis.api.view

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class UserView(
    val id: UInt? = null,
    val name: String,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val password: String? = null
)