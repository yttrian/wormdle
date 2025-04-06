package org.yttr.wormdle.mvc

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*

// https://github.com/yttrian/ktor-mvc/blob/master/src/web/mvc/Controller.kt
abstract class Controller<in M>(private val view: View<M>) {
    abstract fun Route.routes()

    operator fun invoke(route: Route) = with(route) { routes() }

    suspend fun ApplicationCall.respondView(model: M) = this.respondHtmlTemplate(LayoutTemplate(request.origin)) {
        with(view) { apply(model) }
    }
}

fun <M> Route.route(path: String, controller: Controller<M>) = controller(createRouteFromPath(path))
