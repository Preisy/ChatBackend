package ru.preis.ru.preis.api.service.modelConversion

import ru.preis.api.model.MessageModel
import ru.preis.api.model.RoomModel
import ru.preis.api.model.UserModel
import ru.preis.api.view.MessageView
import ru.preis.api.view.RoomView
import ru.preis.api.view.UserView
import ru.preis.database.model.Users
import ru.preis.database.unitOfWork.UnitOfWork

object ModelConverter {
    fun makeView(dao: UserModel): UserView {
        return UserView(
            id = dao.id,
            name = dao.name
        )
    }

    fun makeModel(dto: UserView): UserModel {
        if (dto.password == null)
            throw ModelConversionException("To convert View to Model password should be not null")

        return UserModel(
            name = dto.name,
            password = dto.password.hashCode()
        )
    }

    fun makeView(dao: RoomModel): RoomView {
        return RoomView(
            id = dao.id,
            adminId = dao.adminId
        )
    }

    fun makeModel(dto: RoomView): RoomModel {
        return RoomModel(
            adminId = dto.adminId
        )
    }

    suspend fun makeView(dao: MessageModel, unitOfWork: UnitOfWork = UnitOfWork()): MessageView {
        var member = unitOfWork.getRepository<UserModel>().findFirstOrNull {
            Users.id eq dao.memberId
        }
        return MessageView(
            id = dao.id,
            roomId = dao.roomId,
            memberId = dao.memberId,
            memberName = member?.name,
            message = dao.message,
            datetime = dao.datetime
        )
    }

    fun makeModel(dto: MessageView): MessageModel {
        return MessageModel(
            roomId = dto.roomId,
            memberId = dto.memberId,
            message = dto.message,
        )
    }
}