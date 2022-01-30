package org.yttr.lordle.game

import io.ktor.routing.Route
import io.ktor.routing.get
import org.yttr.lordle.mvc.Controller

object GameController : Controller<GameModel>(GameView) {
    override fun Route.routes() {
        get {
            context.respondView(GameModel("Hello world!", emptyList()))
        }
    }
}
