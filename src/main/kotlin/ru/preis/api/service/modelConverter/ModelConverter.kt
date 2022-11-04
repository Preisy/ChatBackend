package ru.preis.ru.preis.api.service.modelConversion

import ru.preis.api.model.MessageDTO
import ru.preis.api.model.RoomDTO
import ru.preis.api.model.UserDTO
import ru.preis.database.model.MessageDAO
import ru.preis.database.model.RoomDAO
import ru.preis.database.model.UserDAO

object ModelConverter {
    fun makeDTO(dao: UserDAO): UserDTO {
        return UserDTO(
            id = dao.id,
            name = dao.name
        )
    }
    fun makeDAO(dto: UserDTO): UserDAO {
        if (dto.password == null)
            throw ModelConversionException("To convert DTO to DAO password should be not null")

        return UserDAO(
            name = dto.name,
            password = dto.password.hashCode()
        )
    }

    fun makeDTO(dao: RoomDAO): RoomDTO {
        return RoomDTO(
            id = dao.id,
            adminId = dao.adminId
        )
    }
    fun makeDAO(dto: RoomDTO): RoomDAO {
        return RoomDAO(
            adminId = dto.adminId
        )
    }

    fun makeDTO(dao: MessageDAO): MessageDTO {
        return MessageDTO(
            id = dao.id,
            roomId = dao.roomId,
            memberId = dao.memberId,
            message = dao.message,
//            datetime = dao.datetime
        )
    }
    fun makeDAO(dto: MessageDTO): MessageDAO {
        return MessageDAO(
            roomId = dto.roomId,
            memberId = dto.memberId,
            message = dto.message,
//            datetime = dto.datetime
        )
    }
}