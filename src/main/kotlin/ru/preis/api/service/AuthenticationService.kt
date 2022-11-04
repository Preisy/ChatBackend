package ru.preis.api.service

import ru.preis.database.model.UserDAO
import ru.preis.database.unitOfWork.*
import ru.preis.api.model.UserDTO
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

class AuthenticationService(
    _unitOfWork: UnitOfWork = UnitOfWork()
) {
    val unitOfWork = _unitOfWork

    suspend fun logIn(user: UserDTO): Boolean {
        val userHashedPassword = user.password.hashCode()
        val res = unitOfWork.getRepository<UserDAO>().findSingleOrNull {
            it.name == user.name && it.password == userHashedPassword
        }
        return res != null
    }

    suspend fun signUp(user: UserDTO): Boolean { // todo сделать по нормальному
        val res = unitOfWork.getRepository<UserDAO>().findSingleOrNull {
            it.name == user.name
        }
        if (res != null) return false

        unitOfWork.getRepository<UserDAO>().add(
            ModelConverter.makeDAO(user)
        )
        return true
    }
}