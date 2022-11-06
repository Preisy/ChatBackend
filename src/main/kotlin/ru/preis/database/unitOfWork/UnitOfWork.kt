package ru.preis.database.unitOfWork

import ru.preis.api.model.*
import ru.preis.database.repositories.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class UnitOfWork {
    val repositories = mapOf<KType, Lazy<Any>>(
        typeOf<UserModel>() to lazy { UsersRepository() },
        typeOf<UserRoomRelationModel>() to lazy { UserRoomRelationsRepository() },
        typeOf<RoomModel>() to lazy { RoomsRepository() },
        typeOf<MessageModel>() to lazy { MessagesRepository() }
    )


    inline fun <reified T : Model> getRepository(): Repository<T> {
        if (T::class == Model::class) {
            throw ClassCastException("Type parameter must not be the DAOModel")
        }
        return (repositories[typeOf<T>()] as Lazy<Repository<T>>).value
    }
}