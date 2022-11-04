package ru.preis.database.unitOfWork

import ru.preis.database.model.DAOModel
import ru.preis.database.model.RoomDAO
import ru.preis.database.model.UserDAO
import ru.preis.database.model.MessageDAO
import ru.preis.database.model.UserRoomRelationDAO
import ru.preis.database.repositories.*
import java.lang.ClassCastException
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class UnitOfWork {
    val repositories = mapOf<KType, Lazy<Any>>(
        typeOf<UserDAO>() to lazy { UsersRepository() },
        typeOf<UserRoomRelationDAO>() to lazy { UserRoomRelationsRepository() },
        typeOf<RoomDAO>() to lazy { RoomsRepository() },
        typeOf<MessageDAO>() to lazy { MessagesRepository() }
    )


    inline fun <reified T : DAOModel> getRepository(): Repository<T> {
        if (T::class == DAOModel::class) {
            throw ClassCastException("Type parameter must not be the DAOModel")
        }
        return (repositories[typeOf<T>()] as Lazy<Repository<T>>).value
    }
}