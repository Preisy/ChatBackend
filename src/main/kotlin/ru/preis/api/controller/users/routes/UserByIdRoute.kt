package ru.preis.api.controller.users

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.users.resources.UsersResources
import ru.preis.api.model.UserModel
import ru.preis.database.model.Users
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.userByIdRoute(unitOfWork: UnitOfWork) {
    get<UsersResources.Id> { user ->
        unitOfWork.getRepository<UserModel>().findFirstOrNull {
            Users.id eq user.id
        }?.also {
            call.respond(ModelConverter.makeView(it))
        }
    }
}