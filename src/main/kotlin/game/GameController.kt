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
import org.yttr.lordle.WormdleSession
import org.yttr.lordle.mvc.Controller
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

object GameController : Controller<GameModel>(GameView) {
    const val WORD_LENGTH: Int = 5
    const val MAX_ATTEMPTS: Int = 6
    private const val WORD_LIST_SPLITS = 3

    private val config = ConfigFactory.load()
    private val epoch = LocalDate.parse(config.getString("lordle.epoch"))
    private val sources by lazy {
        val lines = javaClass.classLoader.getResource("words.txt")?.readText()?.lines()
        lines?.filter { it.isNotBlank() }?.associate {
            val (word, slug, name) = it.split(" ", limit = WORD_LIST_SPLITS)
            word to (slug to name)
        } ?: emptyMap()
    }
    private val words by lazy {
        val random = Random(config.getInt("lordle.seed"))
        sources.keys.shuffled(random)
    }

    private var ApplicationCall.lordleSession: WormdleSession
        get() {
            val day = ChronoUnit.DAYS.between(epoch, LocalDate.now()).toInt()
            val session = sessions.get<WormdleSession>()?.takeIf { it.day == day } ?: WormdleSession(day)
            sessions.set(session)
            return session
        }
        set(value) = sessions.set(value)

    override fun Route.routes() {
        get {
            val session = context.lordleSession
            val word = words.getOrNull(session.day)

            if (word != null) {
                val (slug, name) = sources.getValue(word)
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
                    entry == words.getOrNull(session.day) -> WormdleSession(
                        session.day,
                        session.guesses + entry,
                        "You got it!"
                    )
                    words.contains(entry) -> WormdleSession(session.day, session.guesses + entry)
                    else -> WormdleSession(session.day, session.guesses, "Not in word list")
                }
            }

            context.response.headers.append(HttpHeaders.Location, context.request.uri)
            context.respond(HttpStatusCode.SeeOther)
        }
    }
}
