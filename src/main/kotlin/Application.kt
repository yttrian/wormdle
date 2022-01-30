package org.yttr.lordle

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ConditionalHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.webjars.Webjars
import kotlinx.css.CssBuilder
import org.yttr.lordle.game.GameController
import org.yttr.lordle.mvc.route
import org.yttr.lordle.style.LordleStyle
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
        route("/", GameController)

        // https://ktor.io/docs/css-dsl.html#use_css
        get("/lordle.css") {
            with(LordleStyle) {
                context.respondText(CssBuilder().apply { apply() }.toString(), ContentType.Text.CSS)
            }
        }
    }
}
