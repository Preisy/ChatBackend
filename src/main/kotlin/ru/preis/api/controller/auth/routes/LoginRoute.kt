package ru.preis.api.controller.auth.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import ru.preis.api.view.UserView
import ru.preis.api.controller.auth.AuthenticationController
import ru.preis.api.sessions.UserSession
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.loginRoute(auth: AuthenticationController) {
    post("/login") {
        val userView = call.receive<UserView>()
        val userModel = auth.findUserByCredentials(userView)
        if (userModel?.id != null) {
            call.sessions.set(UserSession(userId = userModel.id))
            call.respond(
                ModelConverter.makeView(userModel)
            )
        } else
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
    }
}