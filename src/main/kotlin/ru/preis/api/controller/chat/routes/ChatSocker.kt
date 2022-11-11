package ru.preis.api.controller.chat

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.consumeEach
import ru.preis.api.model.RoomModel
import ru.preis.api.sessions.UserSession
import ru.preis.api.view.UserView
import ru.preis.database.model.Rooms
import ru.preis.database.unitOfWork.UnitOfWork
import kotlinx.serialization.*
import ru.preis.api.controller.chat.exceptions.InvalidUserIdException
import ru.preis.api.controller.chat.exceptions.MemberAlreadyExistsException


fun Route.chatSocket(unitOfWork: UnitOfWork) {
    val chatController = ChatController()

    webSocket("/rooms/{id}") {
        val roomId = call.parameters["id"]?.toUIntOrNull()
        if (roomId == null) close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Room not found"))
        val _room = unitOfWork.getRepository<RoomModel>().findFirstOrNull { Rooms.id eq roomId!! }
        if (_room == null)
            close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Room not found"))


        if (call.sessions.get<UserSession>() == null)
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "You should be authorized"))


        if (_room!!.id != roomId)
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "This is inaccessible for you"))

        var user = UserView(name = "")
        try {
            user = chatController.onJoin(
                call.sessions.get<UserSession>()!!,
                this
            )
        } catch (e: NoSuchElementException) {
            call.response.status(HttpStatusCode.NotFound)
            close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "No such user"))
        } catch (e: MemberAlreadyExistsException) {
            call.response.status(HttpStatusCode.Conflict)
            close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Member already exists"))
        } catch (e: InvalidUserIdException) {
            call.response.status(HttpStatusCode.Forbidden)
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "Forbidden"))
        } catch (e: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            close(CloseReason(CloseReason.Codes.INTERNAL_ERROR, "Internal server error"))
            e.printStackTrace()
        }

        try {

            incoming.consumeEach{ frame ->
                try {
                    chatController.transaction(user.id!!, roomId!!, frame)
                } catch (e: SerializationException) {
                    val a = 0;
                    e.printStackTrace()
                }
            }

        } catch (e: ClosedReceiveChannelException) {
            call.response.status(HttpStatusCode.OK)
        } catch (e: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            e.printStackTrace()
        } finally {
            chatController.tryDisconnect(
                user,
                CloseReason(
                    CloseReason.Codes.INTERNAL_ERROR,
                    call.response.status()?.toString() ?: "InternalServerError"
                )
            )
        }

    }
}