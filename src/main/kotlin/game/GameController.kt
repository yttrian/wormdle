package org.yttr.lordle.game

import com.typesafe.config.ConfigFactory
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveParameters
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.yttr.lordle.LordleSession
import org.yttr.lordle.mvc.Controller
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

object GameController : Controller<GameModel>(GameView) {
    const val WORD_LENGTH: Int = 5
    const val MAX_ATTEMPTS: Int = 6

    private val config = ConfigFactory.load()
    private val random = Random(config.getInt("lordle.seed"))
    private val epoch = LocalDate.parse(config.getString("lordle.epoch"))
    private val words = javaClass.classLoader.getResource("words.txt")?.readText()?.lines()?.shuffled(random)

    private var ApplicationCall.lordleSession: LordleSession
        get() {
            val day = ChronoUnit.DAYS.between(epoch, LocalDate.now()).toInt()
            val session = sessions.get<LordleSession>()?.takeIf { it.day == day } ?: LordleSession(day)
            sessions.set(session)
            return session
        }
        set(value) = sessions.set(value)

    override fun Route.routes() {
        get {
            val session = context.lordleSession
            val word = words?.getOrNull(session.day)

            if (word != null) {
                context.respondView(GameModel(word, session.guesses))
            } else {
                context.respondText("No more words!")
            }
        }

        post {
            val session = context.lordleSession
            val formParameters = context.receiveParameters()

            val entry = (0 until WORD_LENGTH).map { l -> formParameters["l$l"] }.joinToString("")

            if (session.guesses.size <= MAX_ATTEMPTS) {
                context.lordleSession = LordleSession(session.day, session.guesses + entry)
            }

            context.response.headers.append(HttpHeaders.Location, context.request.uri)
            context.respond(HttpStatusCode.SeeOther)
        }
    }
}
