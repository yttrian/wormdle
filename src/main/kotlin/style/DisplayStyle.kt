package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.GridTemplateColumns
import kotlinx.css.JustifyContent
import kotlinx.css.TextTransform
import kotlinx.css.alignContent
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.border
import kotlinx.css.borderColor
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.flex
import kotlinx.css.fontSize
import kotlinx.css.gridTemplateColumns
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.pct
import kotlinx.css.rem
import kotlinx.css.textTransform
import kotlinx.css.width
import org.yttr.lordle.game.GameController
import org.yttr.lordle.game.Marker

object DisplayStyle : Style {
    override fun CssBuilder.apply() {
        ".display" {
            display = Display.grid
            flex(1.0, 1.0, 0.pct)
            justifyContent = JustifyContent.center
            alignContent = Align.center
        }

        ".letters" {
            display = Display.grid
            gridTemplateColumns = GridTemplateColumns.repeat("${GameController.WORD_LENGTH}, 1fr")
        }

        ".letter" {
            textTransform = TextTransform.uppercase
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
            height = 3.rem
            width = 3.rem
            color = Color.white
            margin = ".2rem"
            border = ".2rem solid white"
            fontSize = 2.rem
        }

        ".letter.here" {
            color = Color.black
            backgroundColor = Marker.Here.color
            borderColor = Marker.Here.color
        }

        ".letter.somewhere" {
            color = Color.black
            backgroundColor = Marker.Somewhere.color
            borderColor = Marker.Somewhere.color
        }

        ".letter.nowhere" {
            backgroundColor = Marker.Nowhere.color
            borderColor = Marker.Nowhere.color
        }

        ".letters input + span" {
            display = Display.none
        }

        ".letters input:checked + span" {
            display = Display.inlineBlock
        }
    }
}
