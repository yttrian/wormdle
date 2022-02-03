package org.yttr.lordle.game

import org.yttr.lordle.LordleSession

data class GameModel(val word: String, val session: LordleSession) {
    val isWin = session.guesses.lastOrNull() == word
    val isOver = isWin || session.guesses.size >= GameController.MAX_ATTEMPTS
}
