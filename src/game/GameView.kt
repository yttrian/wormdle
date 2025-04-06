package org.yttr.wormdle.game

import kotlinx.html.*
import org.yttr.wormdle.mvc.LayoutTemplate
import org.yttr.wormdle.mvc.View
import org.yttr.wormdle.words.Words
import org.yttr.wormdle.words.link

object GameView : View<GameModel> {
    private const val WORD_LENGTH: Int = GameController.WORD_LENGTH
    private const val MAX_ATTEMPTS: Int = GameController.MAX_ATTEMPTS
    private val letters = 'a'..'z'

    override fun LayoutTemplate.apply(model: GameModel) {
        content {
            val title = "Wormdle ${model.session.day + 1}"
            header {
                h1 { +"$title  of ∞" }
                small { +"Resets daily at 9 AM Pacific. Words repeat on average every ${Words.totalSolutions()} days." }
            }

            input(InputType.checkBox) {
                id = "modal"
                checked = model.isOver
            }
            label("modal") {
                htmlFor = "modal"
                textArea {
                    readonly = true
                    val finalCount = if (model.isWin) model.session.guesses.size.toString() else "X"
                    +"$title $finalCount/$MAX_ATTEMPTS\n\n"
                    +model.session.guesses.joinToString("\n") { guess ->
                        Marker.generate(model.solution.word, guess).joinToString("") { it.emoji }
                    }
                }
            }

            form(method = FormMethod.post, classes = hintClasses(model).joinToString(" ")) {
                div("display") {
                    letters(model)
                }

                div("notes") {
                    div {
                        model.session.message?.let {
                            +it
                            if (model.isOver) {
                                br()
                            }
                        }
                        if (model.isOver) {
                            +"\"${model.solution.word}\" from "
                            a(model.solution.link()) {
                                +model.solution.name
                            }
                        }
                    }
                }

                keyboards(model)
            }
        }
    }

    private fun hintClasses(model: GameModel): List<String> {
        val here = model.solution.word.mapIndexedNotNull { index, c ->
            if (model.session.guesses.any { it[index] == c }) "$c-here" else null
        }

        val wordLetters = model.solution.word.toSet()
        val guessedLetters = model.session.guesses.flatMap { it.toSet() }

        val somewhere = guessedLetters.intersect(wordLetters).map { c -> "$c-somewhere" }

        val nowhere = guessedLetters.minus(wordLetters).map { c -> "$c-nowhere" }

        return here + somewhere + nowhere
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
                                    checked = c == model.retry?.getOrNull(l)
                                    disabled = (l == WORD_LENGTH || model.isOver)
                                }
                                span { +c.toString() }
                            }
                        }
                    }
                } else {
                    val guess = model.session.guesses.getOrNull(attempt)
                    val markers = Marker.generate(model.solution.word, guess ?: "")
                    (0 until WORD_LENGTH).forEach { l ->
                        val c = guess?.getOrNull(l)?.toString() ?: ""
                        div("letter " + (markers.getOrNull(l)?.toString() ?: "")) {
                            span { +c }
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.keyboards(model: GameModel) {
        div("keyboard") {
            (0 until WORD_LENGTH).forEach { l ->
                input(InputType.radio) {
                    id = "l$l" + "e"
                    name = "l$l"
                    checked = model.retry == null
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
