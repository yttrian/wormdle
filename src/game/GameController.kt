package org.yttr.wormdle.game

import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.yttr.wormdle.WormdleSession
import org.yttr.wormdle.mvc.Controller
import org.yttr.wormdle.words.Words
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

object GameController : Controller<GameModel>(GameView) {
    const val WORD_LENGTH: Int = 5
    const val MAX_ATTEMPTS: Int = 6
    private const val DAILY_RESET = 9

    private val config = ConfigFactory.load().getConfig("wormdle")
    private val timezone = ZoneId.of("America/Los_Angeles")
    private val epoch = LocalDate.parse(config.getString("epoch")).atTime(DAILY_RESET, 0).atZone(timezone)

    private var ApplicationCall.wormdleSession: WormdleSession
        get() {
            val day = ChronoUnit.DAYS.between(epoch, ZonedDateTime.now(timezone)).toInt()
            val session = sessions.get<WormdleSession>()?.takeIf { it.day == day } ?: WormdleSession(day)
            sessions.set(session)
            return session
        }
        set(value) = sessions.set(value)

    override fun Route.routes() {
        get {
            val session = call.wormdleSession
            val solution = Words.forDay(session.day)

            call.respondView(GameModel(solution, session))
        }

        post {
            val session = call.wormdleSession
            val formParameters = call.receiveParameters()

            val entry = (0 until WORD_LENGTH).map { l ->
                formParameters["l$l"]?.lowercase()
            }.joinToString("")

            if (session.guesses.size <= MAX_ATTEMPTS && entry.all { it in 'a'..'z' }) {
                call.wormdleSession = when {
                    entry == Words.forDay(session.day).word ->
                        WormdleSession(
                            day = session.day,
                            guesses = session.guesses + entry,
                            last = entry,
                            message = "You got it!"
                        )

                    Words.inDictionary(entry) -> WormdleSession(session.day, session.guesses + entry)
                    else -> WormdleSession(session.day, session.guesses, entry, "Not in the dictionary")
                }
            }

            call.response.headers.append(HttpHeaders.Location, call.request.uri)
            call.respond(HttpStatusCode.SeeOther)
        }
    }
}
