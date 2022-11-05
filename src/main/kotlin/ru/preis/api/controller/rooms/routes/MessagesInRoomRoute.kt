package ru.preis.api.controller.rooms.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.rooms.resources.Rooms
import ru.preis.api.controller.rooms.resources.SortType
import ru.preis.api.model.MessageDTO
import ru.preis.database.model.MessageDAO
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.messagesInRoomRoute(unitOfWork: UnitOfWork) {
    get<Rooms.Id.Messages> { req ->
        val comp = when (req.sort) {
            SortType.DATETIME_LESS -> { it1: MessageDTO, it2: MessageDTO ->
                it2.datetime!!.compareTo(it1.datetime!!)
            }

            SortType.DATETIME_GREATER -> { it1: MessageDTO, it2: MessageDTO ->
                it1.datetime!!.compareTo(it2.datetime!!)
            }
        }
        val offset = req.offset ?: 0u
        val limit = req.limit


        val roomId = req.parent.id
        val messages = unitOfWork.getRepository<MessageDAO>().find {
            it.roomId == roomId
        }.map {
            ModelConverter.makeDTO(it)
        }.sortedWith(comp)

        if (offset >= messages.size.toUInt() || limit == 0u) {
            call.response.status(HttpStatusCode.RequestedRangeNotSatisfiable)
            return@get
        }

        if (limit == null || offset + limit >= messages.size.toUInt())
            call.respond(messages.drop(offset.toInt()))
        else {
            val a = messages.slice(offset.toInt() until (offset + limit).toInt())
            call.respond(a)
        }
    }
}