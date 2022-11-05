package ru.preis.database.unitOfWork

import ru.preis.database.model.*
import ru.preis.database.repositories.*
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