package org.yttr.lordle.mvc

interface View<in T> {
    fun LayoutTemplate.apply(model: T)
}
