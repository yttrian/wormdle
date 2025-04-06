package org.yttr.wormdle

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.webjars.*
import kotlinx.serialization.Serializable
import org.yttr.wormdle.game.GameController
import org.yttr.wormdle.mvc.route
import org.yttr.wormdle.style.WormdleStyle
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private const val DAYS_IN_YEAR: Long = 365
private val oneYear = Duration.ofDays(DAYS_IN_YEAR).toSeconds()

@Suppress("unused", "LongMethod")
fun Application.module() {
    install(CallLogging)

    install(CachingHeaders) {
        val cacheOneYear = CachingOptions(CacheControl.MaxAge(maxAgeSeconds = oneYear.toInt()))
        options { _, content ->
            when (content.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> cacheOneYear
                ContentType.Text.JavaScript -> cacheOneYear
                ContentType.Image.XIcon -> cacheOneYear
                else -> null
            }
        }
    }

    install(DefaultHeaders) {
        header(
            "Content-Security-Policy",
            listOf(
                "default-src 'none'",
                "style-src 'self' 'unsafe-inline'",
                "script-src 'self'",
                "img-src 'self'",
                "connect-src 'self'",
                "frame-ancestors 'none'",
                "base-uri 'self'",
                "form-action 'none'"
            ).joinToString("; ")
        )
        header("X-Frame-Options", "SAMEORIGIN")
        header("X-Content-Type-Options", "nosniff")
        header("Referrer-Policy", "no-referrer")
    }

    install(Webjars)

    install(Sessions) {
        cookie<WormdleSession>("WORMDLE_STATE") {
            cookie.maxAgeInSeconds = oneYear
            cookie.secure = true
            cookie.encoding = CookieEncoding.BASE64_ENCODING
        }
    }

    routing {
        route("/", GameController)

        // https://ktor.io/docs/css-dsl.html#use_css
        get("/${WormdleStyle.filename}") {
            call.respondText(WormdleStyle.content, ContentType.Text.CSS)
        }

        staticResources("images", "images")
    }
}

@Serializable
data class WormdleSession(
    val day: Int,
    val guesses: List<String> = emptyList(),
    val last: String? = null,
    val message: String? = null
)
