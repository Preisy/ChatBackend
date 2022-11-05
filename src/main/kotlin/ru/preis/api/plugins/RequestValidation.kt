package ru.preis.api.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import ru.preis.api.model.UserDTO

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<UserDTO> { user ->
            if (user.name.length < 2)
                ValidationResult.Invalid("A user name length should be greater than 1")
            else
                ValidationResult.Valid
        }
        // todo add message validation
    }
}