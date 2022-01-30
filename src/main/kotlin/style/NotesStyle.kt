package org.yttr.lordle.style

import kotlinx.css.CssBuilder
import kotlinx.css.TextAlign
import kotlinx.css.flex
import kotlinx.css.pct
import kotlinx.css.textAlign

object NotesStyle : Style {
    override fun CssBuilder.apply() {
        ".notes" {
            flex(1.0, 1.0, 0.pct)
            textAlign = TextAlign.center
        }
    }
}
