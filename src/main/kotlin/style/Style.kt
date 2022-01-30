package org.yttr.lordle.style

import kotlinx.css.CssBuilder

interface Style {
    fun CssBuilder.apply()

    fun CssBuilder.append(style: Style) {
        with(style) { apply() }
    }
}
