package ru.preis.api.controller.rooms.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.rooms.resources.Rooms
import ru.preis.database.model.RoomDAO
import ru.preis.database.model.UserRoomRelationDAO
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.allRoomsRoute(unitOfWork: UnitOfWork) {
    get<Rooms> { req ->
        val offset = req.offset ?: 0u
        val limit = req.limit
        val userId = call.receiveParameters()["userId"]?.toUIntOrNull()
        if (userId == null) {
            call.response.status(HttpStatusCode.BadRequest)
            return@get
        }

        val userRoomsIds = unitOfWork.getRepository<UserRoomRelationDAO>().find {
            it.userId == userId
        }.map { it.roomId }

        val rooms = unitOfWork.getRepository<RoomDAO>().find {
            it.id in userRoomsIds
        }.map {
            ModelConverter.makeDTO(it)
        }

        if (rooms.isEmpty()) {
            call.response.status(HttpStatusCode.NotFound)
            return@get
        }
        if (offset >= rooms.size.toUInt() || limit == 0u) {
            call.response.status(HttpStatusCode.RequestedRangeNotSatisfiable)
            return@get
        }

        if (limit == null || offset + limit >= rooms.size.toUInt())
            call.respond(rooms.drop(offset.toInt()))
        else {
            val a = rooms.slice(offset.toInt() until (offset + limit).toInt())
            call.respond(a)
        }
    }
}