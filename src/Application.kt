package org.yttr.lordle

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.webjars.*
import org.yttr.lordle.game.GameController
import org.yttr.lordle.mvc.route
import org.yttr.lordle.style.WormdleStyle
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

private const val DAYS_IN_YEAR: Long = 365
private val oneYear = Duration.ofDays(DAYS_IN_YEAR).toSeconds()

@Suppress("unused", "LongMethod")
fun Application.module() {
    install(AutoHeadResponse)

    install(CallLogging)

    install(Compression)

    install(ConditionalHeaders)

    install(CachingHeaders) {
        val cacheOneYear = CachingOptions(CacheControl.MaxAge(maxAgeSeconds = oneYear.toInt()))
        options { outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> cacheOneYear
                ContentType.Text.JavaScript -> cacheOneYear
                ContentType.Image.XIcon -> cacheOneYear
                else -> null
            }
        }
    }

    install(XForwardedHeaderSupport)

    if (!developmentMode) {
        install(HttpsRedirect)
        install(HSTS)
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
            cookie.secure = !developmentMode
            cookie.encoding = CookieEncoding.BASE64_ENCODING
        }
    }

    routing {
        route("/", GameController)

        // https://ktor.io/docs/css-dsl.html#use_css
        get("/${WormdleStyle.filename}") {
            context.respondText(WormdleStyle.content, ContentType.Text.CSS)
        }

        static {
            route("images") {
                resources("images")
            }
        }
    }
}

data class WormdleSession(
    val day: Int,
    val guesses: List<String> = emptyList(),
    val last: String? = null,
    val message: String? = null
)
