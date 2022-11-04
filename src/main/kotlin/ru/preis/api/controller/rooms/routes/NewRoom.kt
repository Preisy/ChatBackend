package ru.preis.api.controller.rooms.resources

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import ru.preis.api.model.RoomDTO
import ru.preis.database.model.RoomDAO
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.newRoom(unitOfWork: UnitOfWork) {
    post<Rooms.New> {
        val room = call.receive<RoomDTO>()

        unitOfWork.getRepository<RoomDAO>().add(
            ModelConverter.makeDAO(room)
        )

        call.response.status(HttpStatusCode.Created)
    }

}