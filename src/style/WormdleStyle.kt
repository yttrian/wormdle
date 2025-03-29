package org.yttr.wormdle.style

import kotlinx.css.*

object WormdleStyle : Style() {
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

    val filename by lazy {
        "wormdle.$contentHash.css"
    }

    override fun CssBuilder.apply() {
        html {
            height = 100.pct
        }

        body {
            backgroundColor = Color("#1c1c1c")
            color = Color.white
            fontFamily = defaultFonts.joinToString()
            height = 100.pct
            margin = Margin(0.px)
            padding = Padding(0.px)
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
            flex= Flex(1.0, 0.0, 0.pct)
        }

        append(HeaderStyle)
        append(ModalStyle)
        append(DisplayStyle)
        append(NotesStyle)
        append(KeyboardStyle)
    }
}
