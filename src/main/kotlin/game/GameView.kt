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
    private const val WORD_LENGTH: Int = GameController.WORD_LENGTH
    private const val MAX_ATTEMPTS: Int = GameController.MAX_ATTEMPTS
    private val letters = 'a'..'z'

    override fun LayoutTemplate.apply(model: GameModel) {
        content {
            header {
                h1 { +"Lordle" }
            }

            form(method = FormMethod.post, classes = hintClasses(model).joinToString(" ")) {
                div("display") {
                    letters(model)
                }

                div("notes") {
                    model.session.message?.unaryPlus()
                }

                keyboards()
            }
        }
    }

    private fun hintClasses(model: GameModel): List<String> {
        val at = model.word.mapIndexedNotNull { index, c ->
            if (model.session.guesses.any { it[index] == c }) "$c-at-$index" else null
        }

        val wordLetters = model.word.toSet()
        val guessedLetters = model.session.guesses.flatMap { it.toSet() }

        // FIXME: Count repeated letters and stop reporting when all found
        val somewhere = guessedLetters.intersect(wordLetters).map { c -> "$c-somewhere" }

        val nowhere = guessedLetters.minus(wordLetters).map { c -> "$c-nowhere" }

        return at + somewhere + nowhere
    }

    private fun FlowContent.letters(model: GameModel) {
        val currentAttempt = model.session.guesses.size
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
                                    disabled = (l == WORD_LENGTH || model.session.guesses.lastOrNull() == model.word)
                                }
                                span { +c.toString() }
                            }
                        }
                    }
                } else {
                    val guess = model.session.guesses.getOrNull(attempt)
                    (0 until WORD_LENGTH).forEach { l ->
                        val c = guess?.getOrNull(l)?.toString() ?: ""
                        div("letter $c") {
                            span { +c }
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
                                label("key $c") {
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
