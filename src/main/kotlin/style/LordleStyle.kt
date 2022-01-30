package org.yttr.lordle.style

import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.backgroundColor
import kotlinx.css.body
import kotlinx.css.color
import kotlinx.css.fontFamily

object LordleStyle : Style {
    private val defaultFonts = listOf(
        "-apple-system",
        "BlinkMacSystemFont",
        "Segoe UI",
        "Helvetica",
        "Arial",
        "sans-serif",
        "Apple Color Emoji",
        "Segoe UI Emoji"
    )

    override fun CssBuilder.apply() {
        body {
            backgroundColor = Color("#1c1c1c")
            color = Color.white
            fontFamily = defaultFonts.joinToString()
        }

        append(BoardStyle)
    }
}
