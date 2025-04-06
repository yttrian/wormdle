package org.yttr.wormdle.style

import kotlinx.css.*

object HeaderStyle : Style() {
    override fun CssBuilder.apply() {
        header {
            textAlign = TextAlign.center
            margin = Margin(vertical = .5.rem)
        }

        h1 {
            margin = Margin(0.px)
        }
    }
}
