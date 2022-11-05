package ru.preis.api.controller.rooms

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.rooms.resources.Rooms
import ru.preis.database.model.RoomDAO
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.roomByIdRoute(unitOfWork: UnitOfWork) {
    get<Rooms.Id> { room ->
        unitOfWork.getRepository<RoomDAO>().findFirstOrNull {
            it.id == room.id
        }?.also {
            call.respond(
                ModelConverter.makeDTO(it)
            )
        }
    }
}