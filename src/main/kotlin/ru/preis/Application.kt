package ru.preis

//import com.fasterxml.jackson.databind.SerializationFeature
//import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.resources.*
import ru.preis.api.plugins.*
import ru.preis.database.unitOfWork.DatabaseFactory

fun main() {
    embeddedServer(Netty, port = 8086, host = "0.0.0.0") {
        DatabaseFactory.init()


        configureAuthentication()

        configureContentNegotiating()
        configureRequestValidation()
        configureStatusPages()


        install(Resources)


        configureRouting()
    }.start(wait = true)
}


