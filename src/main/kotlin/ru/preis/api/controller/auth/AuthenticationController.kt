package ru.preis.api.controller.auth

import org.jetbrains.exposed.sql.and
import ru.preis.api.model.UserModel
import ru.preis.api.view.UserView
import ru.preis.database.model.Users
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

class AuthenticationController(
    _unitOfWork: UnitOfWork = UnitOfWork()
) {
    val unitOfWork = _unitOfWork

    suspend fun findIdByCredentials(user: UserView): UInt? {
        val userHashedPassword = user.password.hashCode()
        val res = unitOfWork.getRepository<UserModel>().findSingleOrNull {
            (Users.name eq user.name) and (Users.password eq userHashedPassword)
        }
        return res?.id
    }

    suspend fun signUp(user: UserView): Boolean {
        val res = unitOfWork.getRepository<UserModel>().findSingleOrNull {
            Users.name eq user.name
        }
        if (res != null) return false

        unitOfWork.getRepository<UserModel>().add(
            ModelConverter.makeModel(user)
        )
        return true
    }
}