package ru.preis.api.controller.rooms.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import ru.preis.api.controller.rooms.RoomsController
import ru.preis.api.controller.rooms.resources.Rooms
import ru.preis.api.sessions.UserSession

fun Route.joinToRoomRoute(roomsController: RoomsController) {
    post("/rooms/join") {req ->
        val roomId = call.receiveParameters()["roomId"]?.toUInt()
        if (roomId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val userId = call.sessions.get<UserSession>()?.userId
        if (userId == null) {
            call.response.status(HttpStatusCode.Forbidden)
            return@post
        }
        val userRoomRelationModel = roomsController.addUserToRoom(userId, roomId)
        if (userRoomRelationModel == null) {
            call.respond(HttpStatusCode.BadRequest, "No such element")
        } else {
            call.respond(HttpStatusCode.OK)
        }
    }
}