package ru.preis.ru.preis.api.controller.user.resources

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.preis.api.controller.users.allUsersByIdRoute
import ru.preis.api.controller.users.userByIdRoute
import ru.preis.database.unitOfWork.UnitOfWork

fun Application.userRoutes(unitOfWork: UnitOfWork) {
    routing {
        authenticate("auth-basic") {
            allUsersByIdRoute(unitOfWork)
            userByIdRoute(unitOfWork)
        }
    }
}