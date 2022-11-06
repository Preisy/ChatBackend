package ru.preis.api.controller.auth.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.view.UserView
import ru.preis.api.controller.auth.AuthenticationController

fun Route.signupRoute(auth: AuthenticationController) {
    post("/signup") {
        val user = call.receive<UserView>()
        if (auth.signUp(user))
            call.response.status(HttpStatusCode.Created)
        else
            call.respond(HttpStatusCode.Conflict, "Username is taken")
    }
}