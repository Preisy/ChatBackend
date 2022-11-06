package ru.preis.api.controller.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.preis.api.controller.auth.routes.loginRoute
import ru.preis.api.controller.auth.routes.logoutRoute
import ru.preis.api.controller.auth.routes.signupRoute
import ru.preis.database.unitOfWork.UnitOfWork

fun Application.authRoutes(unitOfWork: UnitOfWork) {
    val auth = AuthenticationController(unitOfWork)

    routing {
        loginRoute(auth)
        signupRoute(auth)
        authenticate("auth-basic") {
            logoutRoute()
        }
    }
}