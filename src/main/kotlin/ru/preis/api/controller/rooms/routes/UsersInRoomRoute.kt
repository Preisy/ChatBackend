package ru.preis.api.controller.rooms.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.rooms.resources.Rooms
import ru.preis.database.model.UserDAO
import ru.preis.database.model.UserRoomRelationDAO
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.usersInRoomRoute(unitOfWork: UnitOfWork) {
    get<Rooms.Id.Users> { req ->
        val roomId = req.parent.id
        val relations = unitOfWork.getRepository<UserRoomRelationDAO>().find {
            it.roomId == roomId
        }

        val res = relations.map { userRoom ->
            val user = unitOfWork.getRepository<UserDAO>().findFirstOrNull {
                it.id == userRoom.userId
            }
            if (user == null)
                UserDAO(id = null, name = "", password = 0)
            else
                ModelConverter.makeDTO(user)
        }

        if (res.isEmpty())
            call.response.status(HttpStatusCode.NotFound)
        else
            call.respond(
                res
            )
    }
}