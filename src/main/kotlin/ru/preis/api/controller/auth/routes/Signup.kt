package ru.preis.api.controller.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.preis.api.model.UserDTO
import ru.preis.api.service.AuthenticationService

fun Route.signup(auth: AuthenticationService) {
    post("/signup") {
        val user = call.receive<UserDTO>()
        if (auth.signUp(user))
            call.response.status(HttpStatusCode.Created) // todo вернуть токен
        else
            call.respond(HttpStatusCode.Conflict, "Username is taken")
    }
}