package org.yttr.wormdle.mvc

interface View<in T> {
    fun LayoutTemplate.apply(model: T)
}
