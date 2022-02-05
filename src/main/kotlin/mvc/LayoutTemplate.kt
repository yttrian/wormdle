package org.yttr.lordle.mvc

import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.lang
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.title
import org.yttr.lordle.game.Marker

class LayoutTemplate : Template<HTML> {
    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        comment("https://github.com/yttrian/wormdle")
        head {
            lang = "en"
            // Required meta tags
            meta(charset = "utf-8")
            meta("viewport", "width=device-width, initial-scale=1, user-scalable=no")
            // Turbo
            comment("Turbo makes things smoother, but is not necessary, keeping with the goal of no-JS required.")
            script(src = "/webjars/hotwired__turbo/turbo.es2017-esm.js") { type = "module" }
            // Social Media
            val description = "Guess the hidden lore in 6 tries. A new puzzle is available daily at reset, for now."
            meta("description", description)
            meta("og:title", "Wormdle - A daily Destiny lordle wordle")
            meta("og:type", "website")
            meta("og:description", description)
            meta("theme-color", Marker.Here.color.value)
            // Custom CSS
            link("/wormdle.css", "stylesheet")
            title("Wormdle")
        }
        body {
            insert(content)
        }
    }
}
