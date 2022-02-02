package org.yttr.lordle.game

import kotlinx.css.Color

sealed class Marker(val color: Color, val emoji: String) {
    object Here : Marker(Color.cornflowerBlue, "🟦")
    object Somewhere : Marker(Color.yellow, "🟨")
    object Nowhere : Marker(Color.black, "⬛")

    companion object {
        fun generate(word: String, guess: String): List<Marker> {
            val mismatchedLetters = guess.zip(word).filter { it.first != it.second }.map { it.second }.toMutableList()
            return guess.zip(word).map { (guessLetter, wordLetter) ->
                when {
                    guessLetter == wordLetter -> Here
                    mismatchedLetters.remove(guessLetter) -> Somewhere
                    else -> Nowhere
                }
            }
        }
    }
}
