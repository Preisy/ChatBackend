package ru.preis.api.controller.users

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.controller.users.resources.UsersResources
import ru.preis.api.model.UserModel
import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.allUsersByIdRoute(unitOfWork: UnitOfWork) {
    get<UsersResources> {
        val users: Iterable<UserModel> = unitOfWork.getRepository<UserModel>().findAll()

        call.respond(users.map { ModelConverter.makeView(it) })
    }
}