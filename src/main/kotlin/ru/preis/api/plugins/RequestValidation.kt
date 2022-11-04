package ru.preis.api.plugins

import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import ru.preis.api.model.UserDTO
import ru.preis.database.model.UserDAO

fun Application.configureRequestValidation() {
    install(RequestValidation) {
//        validate<UserDTO> {
//
//        }
        validate<UserDTO> { user ->
            if (user.name.length == 1)
                ValidationResult.Invalid("A user name length should be greater than 1")
            else
                ValidationResult.Valid
        }
//        validate<UserDAO> { user ->
//            if (user.name.length == 1)
//                ValidationResult.Invalid("A user name length should be greater than 1")
//            else
//                ValidationResult.Valid
//        }

//        validate<String> { bodyText ->
//            if (!bodyText.startsWith("Hello"))
//                ValidationResult.Invalid("Body text should start with 'Hello'")
//            else ValidationResult.Valid
//        }
    }
}