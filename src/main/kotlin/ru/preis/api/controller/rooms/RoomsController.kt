package ru.preis.api.controller.rooms

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.and
import ru.preis.api.controller.rooms.resources.SortType
import ru.preis.api.model.MessageModel
import ru.preis.api.model.RoomModel
import ru.preis.api.model.UserModel
import ru.preis.api.model.UserRoomRelationModel
import ru.preis.api.sessions.UserSession
import ru.preis.api.view.MessageView
import ru.preis.api.view.UserView
import ru.preis.database.model.Messages
import ru.preis.database.model.Rooms
import ru.preis.database.model.UserRoomRelations
import ru.preis.database.model.Users
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

class RoomsController(
    val unitOfWork: UnitOfWork = UnitOfWork()
) {

    suspend fun addUserToRoom(userId: UInt, roomId: UInt): UserRoomRelationModel? {
        val user = unitOfWork.getRepository<UserModel>().findFirstOrNull {
            Users.id eq userId
        }
        val room = unitOfWork.getRepository<RoomModel>().findFirstOrNull {
            Rooms.id eq roomId
        }
        if (user == null || room == null) {
            return null;
        }

        return unitOfWork.getRepository<UserRoomRelationModel>().add(
            UserRoomRelationModel(
                roomId, userId
            )
        )
    }

    fun defineComp(sortType: SortType) = run {
        when (sortType) {
            SortType.DATETIME_LESS -> { it1: MessageView, it2: MessageView ->
                it2.datetime!!.compareTo(it1.datetime!!)
            }

            SortType.DATETIME_GREATER -> { it1: MessageView, it2: MessageView ->
                it1.datetime!!.compareTo(it2.datetime!!)
            }
        }
    }

    fun <T> offsetLimit(list: List<T>, offset: UInt, limit: UInt?): List<T> {
        if (offset + (limit ?: 0u) >= list.size.toUInt() || limit == 0u)
            return emptyList()

        return if (limit == null || offset + limit >= list.size.toUInt())
            list.drop(offset.toInt())
        else {
            list.slice(offset.toInt() until (offset + limit).toInt())
        }
    }

    suspend fun findAllRoomsWithUser(userId: UInt) = run {
        val userRoomsIds = unitOfWork.getRepository<UserRoomRelationModel>().findAll {
            UserRoomRelations.userId eq userId
        }.map { it.roomId }

        unitOfWork.getRepository<RoomModel>().findAll {
            Rooms.id inList userRoomsIds
        }.map {
            ModelConverter.makeView(it)
        }
    }


    suspend fun findAllUsersInRoom(roomId: UInt) = run {
        unitOfWork.getRepository<UserRoomRelationModel>().findAll {
            UserRoomRelations.roomId eq roomId
        }.map { userRoom ->
            val user = unitOfWork.getRepository<UserModel>().findFirstOrNull {
                Users.id eq userRoom.userId
            }
            if (user == null)
                UserView(id = null, name = "")
            else
                ModelConverter.makeView(user)
        }
    }

    suspend fun isUserBelongsRoom(call: ApplicationCall, roomId: UInt) = run {
        val userId = call.sessions.get<UserSession>()!!.userId
        unitOfWork.getRepository<UserRoomRelationModel>().findFirstOrNull {
            (UserRoomRelations.roomId eq roomId) and (UserRoomRelations.userId eq userId)
        } != null
    }

    suspend fun findMessagesInRoom(roomId: UInt, comp: (MessageView, MessageView) -> Int) = run {
        unitOfWork.getRepository<MessageModel>().findAll {
            Messages.roomId eq roomId
        }.map {
            ModelConverter.makeView(it, unitOfWork)
        }.sortedWith(comp)
    }
}