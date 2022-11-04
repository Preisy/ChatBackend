package ru.preis.api.controller.users

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.users.resources.UsersResources
import ru.preis.database.model.UserDAO
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.userByIdRoute(unitOfWork: UnitOfWork) {
    get<UsersResources.Id> { user ->
        unitOfWork.getRepository<UserDAO>().findFirstOrNull {
            it.id == user.id
        }?.also {
            call.respond(ModelConverter.makeDTO(it))
        }
    }
}