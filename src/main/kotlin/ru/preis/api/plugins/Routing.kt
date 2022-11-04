package ru.preis.api.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import ru.preis.api.controller.auth.authRoutes
import ru.preis.api.controller.rooms.messagingRoutes

import ru.preis.database.unitOfWork.UnitOfWork
import ru.preis.ru.preis.api.controller.user.resources.userRoutes


fun Application.configureRouting() {
    val users = listOf("John", "Kate", "Mike")
    val unitOfWork = UnitOfWork()

    authRoutes(unitOfWork)
    messagingRoutes(unitOfWork)
    userRoutes(unitOfWork)

    routing {




//        get("/clicker") {
//            var count: Int
//            if (call.request.cookies["count"] != null)
//                count = call.request.cookies["count"]!!.toInt()
//            else
//                count = 0
//            count++
//            call.response.cookies.append("count", count.toString())
//            call.respondText(count.toString())
//        }
//
//        post("/a") {
//            println(call.receiveText())
//            call.respondText("post a")
//            call.request.headers.forEach { title, data -> println("$title: ${data[0]}") }
//        }
//        get("/") {
//            call.respondText("Hello World!")
//        }
//
//        route("/resources") {
//            get("/{id}") {
//                call.parameters["id"]?.let { stringId ->
//                    stringId.toIntOrNull()?.let { id ->
//                        users[id]?.let {
//                            call.respondText(it)
//                        }
//                    }
//                }
//                call.request.queryParameters["name"]
//            }
//
//        }
    }
}
