package org.yttr.lordle.style

import kotlinx.css.*

object ModalStyle : Style() {
    private const val BACKGROUND_ALPHA: Double = 0.5

    val wormGreen = Color("#b3e174")

    override fun CssBuilder.apply() {
        ".modal" {
            display = Display.none
            position = Position.fixed
            height = 100.pct
            width = 100.pct
            justifyContent = JustifyContent.center
            alignItems = Align.center
            flexDirection = FlexDirection.column
            backgroundColor = Color.black.withAlpha(BACKGROUND_ALPHA)
            put("backdrop-filter", "blur(.2rem)")
        }

        ".modal:before" {
            content = QuotedString("×")
            position = Position.fixed
            top = 1.rem
            right = 1.rem
        }

        ".modal:after" {
            content = QuotedString("Select all, copy, paste and share")
            fontSize = .7.rem
            fontStyle = FontStyle.italic
            marginTop = .2.rem
        }

        "input#modal:checked + .modal" {
            display = Display.flex
        }

        ".modal textarea" {
            fontFamily = "inherit"
            fontWeight = FontWeight.bold
            height = 17.rem
            width = 15.rem
            textAlign = TextAlign.center
            fontSize = 1.5.rem
            padding = "2rem"
            resize = Resize.none
            color = Color.black
            backgroundColor = wormGreen
            borderStyle = BorderStyle.none
            borderRadius = .5.rem
        }
    }
}
