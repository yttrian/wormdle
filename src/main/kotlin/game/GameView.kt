package org.yttr.lordle.game

import kotlinx.html.FlowContent
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.span
import org.yttr.lordle.mvc.LayoutTemplate
import org.yttr.lordle.mvc.View

object GameView : View<GameModel> {
    const val WORD_LENGTH: Int = 5
    private const val MAX_ATTEMPTS: Int = 6
    private const val ALPHABET_LENGTH: Int = 26
    private val letters = (0 until ALPHABET_LENGTH).map { 'a' + it }

    override fun LayoutTemplate.apply(model: GameModel) {
        content {
            header {
                h1 { +"Lordle" }
            }

            form(method = FormMethod.post) {
                div("display") {
                    letters(model)
                }

                div("notes")

                keyboards()
            }
        }
    }

    private fun FlowContent.letters(model: GameModel) {
        val currentAttempt = model.guesses.size
        (0 until MAX_ATTEMPTS).forEach { attempt ->
            div("letters") {
                if (currentAttempt == attempt) {
                    (0 until WORD_LENGTH).forEach { l ->
                        div("letter") {
                            letters.forEach { c ->
                                input(InputType.radio) {
                                    id = "l$l" + "c$c"
                                    name = "l$l"
                                    value = c.toString()
                                    disabled = l == WORD_LENGTH
                                }
                                span { +c.toString() }
                            }
                        }
                    }
                } else {
                    val guess = model.guesses.getOrNull(attempt)
                    (0 until WORD_LENGTH).forEach { l ->
                        div("letter") {
                            span { +(guess?.getOrNull(l)?.toString() ?: "") }
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.keyboards() {
        div("keyboard") {
            (0 until WORD_LENGTH).forEach { l ->
                input(InputType.radio) {
                    id = "l$l" + "e"
                    name = "l$l"
                    checked = true
                }
            }
            (0..WORD_LENGTH).forEach { l ->
                div("board") {
                    id = "l$l" + "b"

                    val rows = listOf("qwertyuiop", "asdfghjkl", "zxcvbnm")
                    rows.forEachIndexed { index, row ->
                        val last = index == rows.size - 1
                        div("row") {
                            if (last) {
                                button {
                                    disabled = l != WORD_LENGTH
                                    +"ent"
                                }
                            }
                            row.forEach { c ->
                                label {
                                    htmlFor = "l$l" + "c$c"
                                    +c.toString()
                                }
                            }
                            if (last) {
                                label {
                                    htmlFor = "l${l - 1}" + "e"
                                    +"del"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
