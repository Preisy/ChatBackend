package ru.preis.api.controller.rooms.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import ru.preis.api.controller.rooms.RoomsController
import ru.preis.api.controller.rooms.resources.Rooms
import ru.preis.api.sessions.UserSession

fun Route.allRoomsRoute(roomsController: RoomsController) {
    get<Rooms> { req ->
        call.response.status(HttpStatusCode.OK)
        val offset = req.offset ?: 0u
        val limit = req.limit
//        val userId = call.receiveParameters()["userId"]?.toUIntOrNull()
        var userId = call.sessions.get<UserSession>()!!.userId
        if (userId == null) {
            call.response.status(HttpStatusCode.BadRequest)
            return@get
        }

        val rooms = roomsController.findAllRoomsWithUser(userId)

        if (rooms.isEmpty()) {
            call.response.status(HttpStatusCode.NotFound)
            return@get
        }

        val res = roomsController.offsetLimit(rooms, offset, limit);

        if (res.isEmpty()) {
            call.response.status(HttpStatusCode.RequestedRangeNotSatisfiable)
        } else {
            call.respond(res);
        }
    }
}