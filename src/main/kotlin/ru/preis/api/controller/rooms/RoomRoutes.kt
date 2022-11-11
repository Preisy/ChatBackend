package ru.preis.api.controller.rooms

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.preis.api.controller.rooms.resources.newRoom
import ru.preis.api.controller.rooms.routes.allRoomsRoute
import ru.preis.api.controller.rooms.routes.joinToRoomRoute
import ru.preis.api.controller.rooms.routes.messagesInRoomRoute
import ru.preis.api.controller.rooms.routes.usersInRoomRoute
import ru.preis.database.unitOfWork.UnitOfWork

fun Application.messagingRoutes(unitOfWork: UnitOfWork) {
    val roomsController = RoomsController(unitOfWork)
    routing {
        authenticate("auth-basic") {
            allRoomsRoute(roomsController)
            newRoom(unitOfWork)
            usersInRoomRoute(roomsController)
            messagesInRoomRoute(roomsController)
            joinToRoomRoute(roomsController)
        }
    }
}