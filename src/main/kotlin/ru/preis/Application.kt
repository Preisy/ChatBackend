package ru.preis

//import com.fasterxml.jackson.databind.SerializationFeature
//import io.ktor.serialization.jackson.*
import io.ktor.http.*
import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*
import org.slf4j.LoggerFactory
import ru.preis.database.unitOfWork.DatabaseFactory
import ru.preis.plugins.*
import java.io.File

fun main() {
    val keyStoreFile = File("build/keystore.jks")
    val keystore = generateCertificate(
        file = keyStoreFile,
        keyAlias = "sampleAlias",
        keyPassword = "foobar",
        jksPassword = "foobar"
    )
    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            host = "localhost"
            port = 8080
        }
        sslConnector(
            keyStore = keystore,
            keyAlias = "sampleAlias",
            keyStorePassword = { "foobar".toCharArray() },
            privateKeyPassword = { "foobar".toCharArray() }) {
            host = "localhost"
            port = 8443
//            port = System.getenv("PORT").toInt()
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }
    embeddedServer(Netty, environment).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Cookie)
        allowHeader(HttpHeaders.AuthenticationInfo)
        allowHeader(HttpHeaders.SetCookie)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowHeader("user_session")
        exposeHeader("user_session")
        allowCredentials = true
    }

    configureAuthentication()
    configureContentNegotiating()
    configureRequestValidation()
    configureStatusPages()
    configureSessions()

    install(Resources)
    configureRouting()

    configureWebSockets()
}


