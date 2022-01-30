package org.yttr.lordle.style

import kotlinx.css.CssBuilder
import kotlinx.css.TextAlign
import kotlinx.css.header
import kotlinx.css.textAlign

object HeaderStyle : Style {
    override fun CssBuilder.apply() {
        header {
            textAlign = TextAlign.center
        }
    }
}
