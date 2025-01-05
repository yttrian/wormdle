package org.yttr.lordle.game

import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.yttr.lordle.WormdleSession
import org.yttr.lordle.mvc.Controller
import org.yttr.lordle.words.Words
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

object GameController : Controller<GameModel>(GameView) {
    const val WORD_LENGTH: Int = 5
    const val MAX_ATTEMPTS: Int = 6
    private const val DAILY_RESET = 9

    private val config = ConfigFactory.load().getConfig("lordle")
    private val timezone = ZoneId.of("America/Los_Angeles")
    private val epoch = LocalDate.parse(config.getString("epoch")).atTime(DAILY_RESET, 0).atZone(timezone)

    private var ApplicationCall.lordleSession: WormdleSession
        get() {
            val day = ChronoUnit.DAYS.between(epoch, ZonedDateTime.now(timezone)).toInt()
            val session = sessions.get<WormdleSession>()?.takeIf { it.day == day } ?: WormdleSession(day)
            sessions.set(session)
            return session
        }
        set(value) = sessions.set(value)

    override fun Route.routes() {
        get {
            val session = context.lordleSession
            val word = Words.forDay(session.day)

            // Attempt to warm the dictionary ahead of POST
            Words.warm()

            if (word != null) {
                val (slug, name) = Words.source(word)
                context.respondView(GameModel(word, slug, name, session))
            } else {
                context.respondText("No more words!")
            }
        }

        post {
            val session = context.lordleSession
            val formParameters = context.receiveParameters()

            val entry = (0 until WORD_LENGTH).map { l ->
                formParameters["l$l"]?.lowercase()
            }.joinToString("")

            if (session.guesses.size <= MAX_ATTEMPTS && entry.all { it in 'a'..'z' }) {
                context.lordleSession = when {
                    entry == Words.forDay(session.day) -> WormdleSession(
                        session.day,
                        session.guesses + entry,
                        entry,
                        "You got it!"
                    )
                    Words.inDictionary(entry) -> WormdleSession(session.day, session.guesses + entry)
                    else -> WormdleSession(session.day, session.guesses, entry, "Not in dictionary")
                }
            }

            context.response.headers.append(HttpHeaders.Location, context.request.uri)
            context.respond(HttpStatusCode.SeeOther)
        }
    }
}
