package ru.preis.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureContentNegotiating() {
    install(ContentNegotiation) {
        json()
    }
}