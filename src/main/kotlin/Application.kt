package org.yttr.lordle

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ConditionalHeaders
import io.ktor.http.ContentType
import io.ktor.http.content.resource
import io.ktor.http.content.static
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.webjars.Webjars
import kotlinx.css.CssBuilder
import org.yttr.lordle.game.GameController
import org.yttr.lordle.mvc.route
import org.yttr.lordle.style.WormdleStyle

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(CallLogging)

    install(Compression)

    install(ConditionalHeaders)

    install(Webjars)

    install(Sessions) {
        cookie<WormdleSession>("WORMDLE_SESSION")
    }

    routing {
        route("/", GameController)

        // https://ktor.io/docs/css-dsl.html#use_css
        get("/wordle.css") {
            with(WormdleStyle) {
                context.respondText(CssBuilder().apply { apply() }.toString(), ContentType.Text.CSS)
            }
        }

        static {
            resource("favicon.ico")
        }
    }
}

data class WormdleSession(
    val day: Int,
    val guesses: List<String> = emptyList(),
    val last: String? = null,
    val message: String? = null
)
