package ru.preis.api.controller.chat

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.preis.database.unitOfWork.UnitOfWork

fun Application.chatRoutes() {
    val unitOfWork = UnitOfWork()

    routing {
//        authenticate("auth-basic") {
        chatSocket(unitOfWork)
//        }
    }


}