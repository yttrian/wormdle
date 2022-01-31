package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.GridTemplateColumns
import kotlinx.css.JustifyContent
import kotlinx.css.TextTransform
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.border
import kotlinx.css.borderColor
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
import org.yttr.lordle.game.GameController

object DisplayStyle : Style {
    private val letters = 'a'..'z'

    override fun CssBuilder.apply() {
        ".display" {
            display = Display.grid
            justifyContent = JustifyContent.center
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

        (0..GameController.WORD_LENGTH).forEach { l ->
            letters.forEach { c ->
                ".$c-at-$l .letter.$c:nth-child(${l + 1})" {
                    color = Color.black
                    backgroundColor = Color.cornflowerBlue
                    borderColor = Color.cornflowerBlue
                }

                ".$c-somewhere .letter.$c" {
                    color = Color.black
                    backgroundColor = Color.yellow
                    borderColor = Color.yellow
                }

                ".$c-nowhere .letter.$c" {
                    backgroundColor = Color.black
                    borderColor = Color.black
                }
            }
        }

        ".letters input + span" {
            display = Display.none
        }

        ".letters input:checked + span" {
            display = Display.inlineBlock
        }
    }
}
