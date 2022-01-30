package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.GridTemplateColumns
import kotlinx.css.JustifyContent
import kotlinx.css.TextTransform
import kotlinx.css.alignItems
import kotlinx.css.border
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.fontSize
import kotlinx.css.gridTemplateColumns
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.rem
import kotlinx.css.textTransform
import kotlinx.css.width
import org.yttr.lordle.game.GameView

object DisplayStyle : Style {
    override fun CssBuilder.apply() {
        ".display" {
            display = Display.grid
            justifyContent = JustifyContent.center
        }

        ".letters" {
            display = Display.grid
            gridTemplateColumns = GridTemplateColumns.repeat("${GameView.WORD_LENGTH}, 1fr")
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

        ".letters span" {
            display = Display.none
        }

        ".letters input:checked + span" {
            display = Display.inlineBlock
        }
    }
}
