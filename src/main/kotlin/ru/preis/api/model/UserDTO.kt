package ru.preis.api.model

//import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.typesafe.config.Optional
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.internal.throwMissingFieldException
import ru.preis.database.model.UserDAO

//@JsonSerialize
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class UserDTO(
    val id: Int? = null,
    val name: String,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val password: String? = null
)