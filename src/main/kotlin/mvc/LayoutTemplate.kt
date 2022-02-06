package org.yttr.lordle.mvc

import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import io.ktor.http.RequestConnectionPoint
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.lang
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.title
import org.yttr.lordle.style.ModalStyle

class LayoutTemplate(request: RequestConnectionPoint) : Template<HTML> {
    private val baseUrl = "https://${request.host}"
    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        comment("https://github.com/yttrian/wormdle")
        head {
            lang = "en"
            comment("Required meta tags")
            meta(charset = "utf-8")
            meta("viewport", "width=device-width, initial-scale=1, user-scalable=no")
            val description = "Guess the hidden lore in 6 tries. A new puzzle is available daily at reset, for now."
            meta("description", description)
            comment("Turbo! Makes things smoother, but is not necessary, keeping with the goal of no-JS required.")
            script(src = "/webjars/hotwired__turbo/turbo.es2017-esm.js") { type = "module" }
            comment("Social media")
            meta("og:url", baseUrl)
            meta("og:title", "Wormdle - A daily Destiny lordle wordle")
            meta("og:type", "website")
            meta("og:description", description)
            meta("og:image", "$baseUrl/images/serindoodles_wormdle_lr.png")
            meta("og:image:alt", "The Wormdle logo created by @serindoodles")
            meta("twitter:card", "summary_large_image")
            meta("theme-color", ModalStyle.wormGreen.value)
            comment("Styles")
            link("/wormdle.css", "stylesheet")
            title("Wormdle")
        }
        body {
            insert(content)
        }
    }
}
