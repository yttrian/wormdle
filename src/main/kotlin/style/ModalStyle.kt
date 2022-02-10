package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.BorderStyle
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.FontStyle
import kotlinx.css.FontWeight
import kotlinx.css.JustifyContent
import kotlinx.css.Position
import kotlinx.css.QuotedString
import kotlinx.css.Resize
import kotlinx.css.TextAlign
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.borderRadius
import kotlinx.css.borderStyle
import kotlinx.css.color
import kotlinx.css.content
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.fontStyle
import kotlinx.css.fontWeight
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.marginTop
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.rem
import kotlinx.css.resize
import kotlinx.css.textAlign
import kotlinx.css.width

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
