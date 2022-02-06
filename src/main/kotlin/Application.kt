package org.yttr.lordle

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.AutoHeadResponse
import io.ktor.features.CachingHeaders
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.features.HSTS
import io.ktor.features.HttpsRedirect
import io.ktor.features.XForwardedHeaderSupport
import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.CookieEncoding
import io.ktor.http.content.CachingOptions
import io.ktor.http.content.resource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.webjars.Webjars
import kotlinx.css.CssBuilder
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
        get("/wormdle.css") {
            with(WormdleStyle) {
                context.respondText(CssBuilder().apply { apply() }.toString(), ContentType.Text.CSS)
            }
        }

        static {
            resource("favicon.ico", "images/favicon.ico")
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
