package ru.preis

//import com.fasterxml.jackson.databind.SerializationFeature
//import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*
import io.ktor.server.websocket.*
import ru.preis.database.unitOfWork.DatabaseFactory
import ru.preis.plugins.*

fun main() { // todo service не должен принимать DTO
    embeddedServer(Netty,
//        port = 8086
        port = System.getenv("PORT").toInt()
        , host = "0.0.0.0") {
        DatabaseFactory.init()

        install(CORS) {
            anyHost()
        }

        configureAuthentication()
        configureContentNegotiating()
        configureRequestValidation()
        configureStatusPages()
        configureSessions()

        install(Resources)
        configureRouting()

        configureWebSockets()
    }.start(wait = true)
}


