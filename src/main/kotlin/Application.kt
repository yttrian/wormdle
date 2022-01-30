package org.yttr.lordle

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ConditionalHeaders
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.webjars.Webjars
import java.time.LocalDate

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(CallLogging)

    install(Compression)

    install(ConditionalHeaders)

    install(Webjars)

    data class LordleSession(val guesses: List<String> = emptyList(), val day: LocalDate)
    install(Sessions) {
        cookie<LordleSession>("LORDLE_SESSION")
    }

    routing {
        get {
            context.respond("Hello world!")
        }
    }
}
