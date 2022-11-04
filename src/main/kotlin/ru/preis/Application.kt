package ru.preis

//import com.fasterxml.jackson.databind.SerializationFeature
//import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.preis.api.plugins.*
import ru.preis.database.unitOfWork.DatabaseFactory
import ru.preis.api.plugins.configureContentNegotiating
import ru.preis.api.plugins.configureRequestValidation
import ru.preis.api.plugins.configureStatusPages
import io.ktor.server.resources.*
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8086, host = "0.0.0.0") {
        DatabaseFactory.init()


        configureAuthentication()

        configureContentNegotiating()
        configureRequestValidation()
        configureStatusPages()


        install(Resources)


        configureRouting()


//
//

//        install(Authentication) {
//            basic("auth-basic") {
//                realm = "Access to the '/' path"
//                validate { credentials ->
//                    if (credentials.name == "jetbrains" && credentials.password == "foobar") {
//                        UserIdPrincipal(credentials.name)
//                    } else {
//                        null
//                    }
//                }
//            }
//        }

//        routing {
//            authenticate("auth-basic") {
//                get("/auth") {
//                    call.respondText("/login")
//                }
//            }
//        }

    }.start(wait = true)
}


