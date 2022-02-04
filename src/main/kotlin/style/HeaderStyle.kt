package org.yttr.lordle.style

import kotlinx.css.CssBuilder
import kotlinx.css.TextAlign
import kotlinx.css.h1
import kotlinx.css.header
import kotlinx.css.margin
import kotlinx.css.textAlign

object HeaderStyle : Style {
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
