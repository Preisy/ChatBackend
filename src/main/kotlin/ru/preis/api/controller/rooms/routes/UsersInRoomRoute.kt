package ru.preis.api.controller.rooms.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.rooms.RoomsController
import ru.preis.api.controller.rooms.resources.Rooms

fun Route.usersInRoomRoute(roomsController: RoomsController) {
    get<Rooms.Id.Users> { req ->
        val roomId = req.parent.id
        if (!roomsController.isUserBelongsRoom(call, roomId)) {
            call.response.status(HttpStatusCode.Forbidden)
            return@get
        }

        val res = roomsController.findAllUsersInRoom(roomId)

        call.respond(
            res
        )
    }
}