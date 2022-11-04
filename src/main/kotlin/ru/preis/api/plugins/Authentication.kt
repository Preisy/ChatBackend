package ru.preis.api.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import ru.preis.api.model.UserDTO
import ru.preis.api.service.AuthenticationService


fun Application.configureAuthentication() {
    val authService = AuthenticationService()

    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                if (authService.logIn(
                        UserDTO(
                            name = credentials.name,
                            password = credentials.password
                        )
                    )
                ) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}