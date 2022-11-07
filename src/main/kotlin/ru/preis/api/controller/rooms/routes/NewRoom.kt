package ru.preis.api.controller.rooms.resources

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import ru.preis.api.model.RoomModel
import ru.preis.api.model.UserRoomRelationModel
import ru.preis.api.sessions.UserSession
import ru.preis.api.view.RoomView
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.newRoom(unitOfWork: UnitOfWork) {
    post<Rooms.New> {
        val adminId = call.sessions.get<UserSession>()!!.userId

        val res = unitOfWork.getRepository<RoomModel>().add(
            RoomModel(adminId = adminId)
        )
        if (res == null) {
            call.response.status(HttpStatusCode.InsufficientStorage)
            return@post
        }

        unitOfWork.getRepository<UserRoomRelationModel>().add(
            UserRoomRelationModel(
                roomId = res.id!!,
                userId = res.adminId
            )
        )


        call.response.status(HttpStatusCode.Created)
    }

}