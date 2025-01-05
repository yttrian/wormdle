package org.yttr.lordle.style

import kotlinx.css.*

object HeaderStyle : Style() {
    override fun CssBuilder.apply() {
        header {
            textAlign = TextAlign.center
            margin = ".5rem 0 .5rem"
        }

        h1 {
            margin = "0"
        }
    }
}
