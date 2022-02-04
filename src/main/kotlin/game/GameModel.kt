package org.yttr.lordle.game

import org.yttr.lordle.WormdleSession

data class GameModel(val word: String, val slug: String, val name: String, val session: WormdleSession) {
    val isWin = session.guesses.lastOrNull() == word
    val isOver = isWin || session.guesses.size >= GameController.MAX_ATTEMPTS
}
