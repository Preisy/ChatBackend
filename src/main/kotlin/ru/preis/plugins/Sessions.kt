package ru.preis.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import ru.preis.api.sessions.UserSession
import kotlin.collections.set

fun Application.configureSessions() {
    install(Sessions) {
        val secretEncryptKey = hex("00112233445566778899aabbccddeeff")
        val secretSignKey = hex("6819b57a326945c1968f45236589")
        cookie<UserSession>("user_session") {
            cookie.extensions["SameSite"] = "Strict"
//            cookie.secure = true

            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }
    }

    intercept(ApplicationCallPipeline.Plugins) {
        if (call.sessions.get<UserSession>() == null &&
            this.context.request.uri != "/login" &&
            this.context.request.uri != "/signup"
        ) {
            call.respond(HttpStatusCode.Unauthorized)
        }
    }
}