package ru.preis.plugins

import io.ktor.server.application.*
import ru.preis.api.controller.auth.authRoutes
import ru.preis.api.controller.rooms.messagingRoutes
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.controller.user.resources.userRoutes


fun Application.configureRouting() {
    val unitOfWork = UnitOfWork()

    authRoutes(unitOfWork)
    messagingRoutes(unitOfWork)
    userRoutes(unitOfWork)
}
