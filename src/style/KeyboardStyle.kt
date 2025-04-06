package org.yttr.wormdle.style

import kotlinx.css.*
import org.yttr.wormdle.game.GameController
import org.yttr.wormdle.game.Marker

object KeyboardStyle : Style() {
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
            margin = Margin(.5.rem)
        }

        ".keyboard label, .keyboard button" {
            color = Color.white
            display = Display.flex
            flex = Flex(1.0, 0.0, 0.pct)
            height = 3.rem
            border = Border.none
            borderRadius = .6.rem
            backgroundColor = Color.gray
            textTransform = TextTransform.uppercase
            justifyContent = JustifyContent.center
            alignItems = Align.center
            cursor = Cursor.pointer
        }

        ('a'..'z').forEach { c ->
            ".$c-here.$c-somewhere .key.$c" {
                backgroundColor = Marker.Here.color
            }

            ".$c-somewhere .key.$c" {
                color = Color.black
                backgroundColor = Marker.Somewhere.color
            }

            ".$c-nowhere .key.$c" {
                backgroundColor = Marker.Nowhere.color
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
