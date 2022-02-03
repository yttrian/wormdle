package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.BorderStyle
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.Position
import kotlinx.css.Resize
import kotlinx.css.TextAlign
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.borderRadius
import kotlinx.css.borderStyle
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.fontSize
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.rem
import kotlinx.css.resize
import kotlinx.css.textAlign
import kotlinx.css.width

object ModalStyle : Style {
    override fun CssBuilder.apply() {
        ".modal" {
            display = Display.none
        }

        "input#modal:checked + .modal" {
            display = Display.flex
            position = Position.fixed
            height = 100.pct
            width = 100.pct
            justifyContent = JustifyContent.center
            alignItems = Align.center
        }

        ".modal textarea" {
            height = 17.rem
            width = 15.rem
            textAlign = TextAlign.center
            fontSize = 1.5.rem
            padding = "2rem"
            resize = Resize.none
            color = Color.white
            backgroundColor = Color.darkSlateGrey
            borderStyle = BorderStyle.none
            borderRadius = .5.rem
        }
    }
}
