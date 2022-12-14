package ru.preis.api.controller.rooms.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.rooms.RoomsController
import ru.preis.api.controller.rooms.resources.Rooms
import ru.preis.api.view.MessageView

fun Route.messagesInRoomRoute(roomsController: RoomsController) {
    get<Rooms.Id.Messages> { req ->
        val comp = roomsController.defineComp(req.sort)
        val offset = req.offset ?: 0u
        val limit = req.limit
        val roomId = req.parent.id

        if (!roomsController.isUserBelongsRoom(call, roomId)) {
            call.response.status(HttpStatusCode.Forbidden)
            return@get
        }


        val messages = roomsController.findMessagesInRoom(roomId, comp)

        if (messages.isEmpty()) {
            call.respond(emptyList<MessageView>())
            return@get
        }

        val res = roomsController.offsetLimit(messages, offset, limit);

        if (res.isEmpty()) {
            call.response.status(HttpStatusCode.RequestedRangeNotSatisfiable)
        } else {
            call.respond(res);
        }
    }
}