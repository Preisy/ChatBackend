package ru.preis.api.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import ru.preis.api.model.MessageDTO
import ru.preis.api.model.UserDTO

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<UserDTO> { user ->
            if (user.name.length < 2)
                ValidationResult.Invalid("Username length must be greater than 1")
            else if (user.name.length > 255)
                ValidationResult.Invalid("Username length must be less than 2")
            else
                ValidationResult.Valid
        }



        validate<MessageDTO> { message ->
            if (message.message.isEmpty())
                ValidationResult.Invalid("Message must Not be empty")
            ValidationResult.Valid
        }
    }
}