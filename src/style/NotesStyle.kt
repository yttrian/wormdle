package org.yttr.wormdle.style

import kotlinx.css.*
import org.yttr.wormdle.game.Marker

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
