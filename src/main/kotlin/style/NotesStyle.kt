package org.yttr.lordle.style

import kotlinx.css.Align
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.TextAlign
import kotlinx.css.alignItems
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.textAlign
import org.yttr.lordle.game.Marker

object NotesStyle : Style() {
    override fun CssBuilder.apply() {
        ".notes" {
            display = Display.flex
            textAlign = TextAlign.center
            alignItems = Align.center
        }

        ".notes a" {
            color = Marker.Here.color
        }
    }
}
