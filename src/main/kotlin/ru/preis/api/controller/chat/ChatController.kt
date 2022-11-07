package ru.preis.api.controller.chat

import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.and
import ru.preis.api.model.MessageModel
import ru.preis.api.model.UserModel
import ru.preis.api.model.UserRoomRelationModel
import ru.preis.api.sessions.UserSession
import ru.preis.api.view.MessageView
import ru.preis.api.view.UserView
import ru.preis.database.model.UserRoomRelations
import ru.preis.database.model.Users
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

class ChatController(
    private val unitOfWork: UnitOfWork = UnitOfWork()
) {
    private val connectedUsers = ConcurrentHashMap<UserView, WebSocketSession>()


    suspend fun transaction(
        userId: UInt,
        roomId: UInt,
        frame: Frame
    ) {
        if (frame !is Frame.Text) return

        val message = Json.decodeFromString<MessageView>(frame.readText())
        if (!validateMessage(message, userId, roomId)) return
        val model = ModelConverter.makeModel(message);
        unitOfWork.getRepository<MessageModel>().add(
            model
        )

        val str = Json.encodeToString(
            ModelConverter.makeView(model, unitOfWork)
        )
        sendAll(str)
    }

    suspend fun sendAll(message: String) {
        connectedUsers.forEach { (_, socket) ->
            socket.send(message)
        }
    }

    private suspend fun validateMessage(message: MessageView, userId: UInt, roomId: UInt): Boolean {
        if (message.memberId != userId) return false
        if (message.roomId != roomId) return false
        if (unitOfWork.getRepository<UserRoomRelationModel>().findFirstOrNull {
                (UserRoomRelations.roomId eq roomId) and (UserRoomRelations.userId eq userId)
            } == null) return false

        return true
    }


    suspend fun onJoin(session: UserSession, socket: WebSocketSession): UserView {
        val user = unitOfWork.getRepository<UserModel>().findFirstOrNull {
            Users.id eq session.userId
        }?.let {
            ModelConverter.makeView(it)
        } ?: throw NoSuchElementException()

        connectedUsers[user] = socket

        println("----------")
        for (i in connectedUsers) {
            println(i.toPair().first)
        }
        println("------------")

        return user
    }

    suspend fun tryDisconnect(user: UserView, closeReason: CloseReason) {
        connectedUsers[user]?.close()
        connectedUsers.remove(user)
    }
}