package org.yttr.lordle.mvc

import io.ktor.html.*
import io.ktor.http.*
import kotlinx.html.*
import org.yttr.lordle.style.ModalStyle
import org.yttr.lordle.style.WormdleStyle

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
            val description = "Guess the hidden lore in 6 tries. A new puzzle is available daily at 9 AM Pacific reset."
            meta("description", description)
            comment("Turbo! Makes things smoother, but is not necessary, keeping with the goal of no-JS required.")
            script(src = "/webjars/hotwired__turbo/turbo.es2017-esm.js") { type = "module" }
            comment("Social media")
            meta("og:url", baseUrl)
            meta("og:title", "Wormdle - A daily Destiny lordle wordle")
            meta("og:type", "website")
            meta("og:description", description)
            meta("og:image", "$baseUrl/images/serindoodles_wormdle.png")
            meta("og:image:alt", "The Wormdle logo created by @serindoodles")
            meta("twitter:card", "summary_large_image")
            meta("theme-color", ModalStyle.wormGreen.value)
            comment("Styles")
            link("/${WormdleStyle.filename}", "stylesheet")
            link("/images/worm_favicon.png", "icon shortcut", "image/png")
            title("Wormdle")
        }
        body {
            insert(content)
        }
    }
}
