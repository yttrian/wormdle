package org.yttr.lordle.style

import kotlinx.css.CssBuilder
import kotlinx.css.fontSize
import kotlinx.css.h1
import kotlinx.css.margin
import kotlinx.css.rem

object BoardStyle : Style {
    override fun CssBuilder.apply() {
        h1 {
            margin = "2rem"
            fontSize = 2.rem
        }
    }
}
