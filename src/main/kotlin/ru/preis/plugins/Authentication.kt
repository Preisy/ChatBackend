package ru.preis.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import ru.preis.api.view.UserView
import ru.preis.api.controller.auth.AuthenticationController


fun Application.configureAuthentication() {
    val authService = AuthenticationController()

    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                if (authService.findIdByCredentials(
                        UserView(
                            name = credentials.name,
                            password = credentials.password
                        )
                    ) != null
                ) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}