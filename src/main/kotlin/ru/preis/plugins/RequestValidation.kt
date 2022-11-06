package ru.preis.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import ru.preis.api.view.MessageView
import ru.preis.api.view.UserView

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<UserView> { user ->
            if (user.name.length < 2)
                ValidationResult.Invalid("Username length must be greater than 1")
            else if (user.name.length > 255)
                ValidationResult.Invalid("Username length must be less than 2")
            else
                ValidationResult.Valid
        }



        validate<MessageView> { message ->
            if (message.message.isEmpty())
                ValidationResult.Invalid("Message must Not be empty")
            ValidationResult.Valid
        }
    }
}