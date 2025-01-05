package org.yttr.lordle.style

import kotlinx.css.*
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
