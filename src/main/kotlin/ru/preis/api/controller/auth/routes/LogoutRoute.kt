package ru.preis.api.controller.auth.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import ru.preis.api.sessions.UserSession

fun Route.logoutRoute() {
    post("/logout") {
        call.sessions.clear<UserSession>()
        call.response.status(HttpStatusCode.OK)
        call.respondRedirect("/login")
    }
}