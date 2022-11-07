package ru.preis.api.controller.auth.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.view.UserView
import ru.preis.api.controller.auth.AuthenticationController
import ru.preis.ru.preis.api.service.modelConversion.ModelConverter

fun Route.signupRoute(auth: AuthenticationController) {
    post("/signup") {
        val userView = call.receive<UserView>()
        val userModel = auth.signUp(userView)
        if (userModel != null) {
            call.respond(
                ModelConverter.makeView(userModel)
            )
            call.response.status(HttpStatusCode.Created)
        } else {
            call.respond(HttpStatusCode.Conflict, "Username is taken")
        }
    }
}