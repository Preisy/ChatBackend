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

fun Route.loginRoute(auth: AuthenticationController) {
    post("/login") {
        val user = call.receive<UserView>()
        val userId = auth.findIdByCredentials(user)
        if (userId != null) {
            call.sessions.set(UserSession(userId = userId))
            call.response.status(HttpStatusCode.OK)
        } else
            call.respond(HttpStatusCode.Conflict, "Invalid credentials")
    }
}