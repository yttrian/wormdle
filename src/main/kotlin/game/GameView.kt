package org.yttr.lordle.game

import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.span
import org.yttr.lordle.mvc.LayoutTemplate
import org.yttr.lordle.mvc.View

object GameView : View<GameModel> {
    private const val WORD_LENGTH: Int = 5
    private const val ALPHABET_LENGTH: Int = 26
    private val letters = (0 until ALPHABET_LENGTH).map { 'a' + it }

    override fun LayoutTemplate.apply(model: GameModel) {
        content {
            form {
                div("letters") {
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
                }

                keyboards()
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
                    button {
                        disabled = l != WORD_LENGTH
                        +"ent"
                    }
                    div {
                        listOf("qwertyuiop", "asdfghjkl", "zxcvbnm").forEach { row ->
                            div {
                                row.forEach { c ->
                                    label {
                                        htmlFor = "l$l" + "c$c"
                                        +c.toString()
                                    }
                                }
                            }
                        }
                    }
                    label {
                        htmlFor = "l${l + 1}" + "e"
                        +"del"
                    }
                }
            }
        }
    }
}
