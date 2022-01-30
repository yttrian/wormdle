package org.yttr.lordle.mvc

import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtmlTemplate
import io.ktor.routing.Route
import io.ktor.routing.createRouteFromPath

// https://github.com/yttrian/ktor-mvc/blob/master/src/web/mvc/Controller.kt
abstract class Controller<in M>(private val view: View<M>) {
    abstract fun Route.routes()

    operator fun invoke(route: Route) = with(route) { routes() }

    suspend fun ApplicationCall.respondView(model: M) = this.respondHtmlTemplate(LayoutTemplate()) {
        with(view) { apply(model) }
    }
}

fun <M> Route.route(path: String, controller: Controller<M>) = controller(createRouteFromPath(path))
