package ru.preis.api.controller.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.api.service.AuthenticationService

fun Application.authRoutes(unitOfWork: UnitOfWork) {
    val auth = AuthenticationService(unitOfWork)

    routing {
        login(auth)
        signup(auth)
    }
}