package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Cursor
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.JustifyContent
import kotlinx.css.TextAlign
import kotlinx.css.TextTransform
import kotlinx.css.UserSelect
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.border
import kotlinx.css.borderRadius
import kotlinx.css.color
import kotlinx.css.cursor
import kotlinx.css.display
import kotlinx.css.flex
import kotlinx.css.flexDirection
import kotlinx.css.gap
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.maxWidth
import kotlinx.css.pct
import kotlinx.css.rem
import kotlinx.css.textAlign
import kotlinx.css.textTransform
import kotlinx.css.userSelect
import kotlinx.css.width
import org.yttr.lordle.game.GameController

object KeyboardStyle : Style {
    override fun CssBuilder.apply() {
        ".keyboard" {
            userSelect = UserSelect.none
            put("touch-action", "manipulation")
            width = 100.pct
            maxWidth = 37.rem
        }

        ".board" {
            width = 100.pct
            textAlign = TextAlign.center
            flexDirection = FlexDirection.column
            alignItems = Align.center
        }

        ".board .row" {
            display = Display.flex
            gap = .5.rem
            margin = ".5rem"
        }

        ".keyboard label, .keyboard button" {
            color = Color.white
            display = Display.flex
            flex(1.0, 0.0, 0.pct)
            height = 3.rem
            border = "none"
            borderRadius = .6.rem
            backgroundColor = Color.gray
            textTransform = TextTransform.uppercase
            justifyContent = JustifyContent.center
            alignItems = Align.center
            cursor = Cursor.pointer
        }

        ('a'..'z').forEach { c ->
            "form[class*='$c-at'] .key.$c" {
                backgroundColor = Color.cornflowerBlue
            }

            ".$c-somewhere .key.$c" {
                color = Color.black
                backgroundColor = Color.yellow
            }

            ".$c-nowhere .key.$c" {
                backgroundColor = Color.black
            }
        }

        (0..GameController.WORD_LENGTH).forEach { l ->
            "input#l${l}e:not(:checked) ~ div#l${l}b" {
                display = Display.none
            }

            "input#l${l - 1}e:checked ~ div#l${l}b" {
                display = Display.none
            }
        }
    }
}
