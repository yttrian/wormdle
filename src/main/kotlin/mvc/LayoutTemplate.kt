package org.yttr.lordle.mvc

import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.title

class LayoutTemplate : Template<HTML> {
    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        head {
            // Required meta tags
            meta(charset = "utf-8")
            meta("viewport", "width=device-width, initial-scale=1, shrink-to-fit=no")
            // Turbo
            script(src = "/webjars/hotwired__turbo/turbo.es2017-esm.js") { type = "module" }
            // Custom CSS
            link("/lordle.css", "stylesheet")
            title("Lordle")
        }
        body {
            insert(content)
        }
    }
}
