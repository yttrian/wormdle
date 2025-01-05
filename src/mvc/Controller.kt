package org.yttr.lordle.mvc

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.routing.*

// https://github.com/yttrian/ktor-mvc/blob/master/src/web/mvc/Controller.kt
abstract class Controller<in M>(private val view: View<M>) {
    abstract fun Route.routes()

    operator fun invoke(route: Route) = with(route) { routes() }

    suspend fun ApplicationCall.respondView(model: M) = this.respondHtmlTemplate(LayoutTemplate(request.origin)) {
        with(view) { apply(model) }
    }
}

fun <M> Route.route(path: String, controller: Controller<M>) = controller(createRouteFromPath(path))
