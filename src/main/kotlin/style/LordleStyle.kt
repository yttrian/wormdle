package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.body
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.flex
import kotlinx.css.flexDirection
import kotlinx.css.fontFamily
import kotlinx.css.form
import kotlinx.css.height
import kotlinx.css.html
import kotlinx.css.input
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct

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
        html {
            height = 100.pct
        }

        body {
            backgroundColor = Color("#1c1c1c")
            color = Color.white
            fontFamily = defaultFonts.joinToString()
            height = 100.pct
            margin = "0"
            padding = "0"
            display = Display.flex
            flexDirection = FlexDirection.column
        }

        input {
            display = Display.none
        }

        form {
            display = Display.flex
            flexDirection = FlexDirection.column
            alignItems = Align.center
            flex(1.0, 0.0, 0.pct)
        }

        append(HeaderStyle)
        append(DisplayStyle)
        append(NotesStyle)
        append(KeyboardStyle)
    }
}
