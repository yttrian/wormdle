package org.yttr.wormdle.game

import org.yttr.wormdle.WormdleSession
import org.yttr.wormdle.words.Solution

data class GameModel(val solution: Solution, val session: WormdleSession) {
    val isWin = session.guesses.lastOrNull() == solution.word
    val isOver = isWin || session.guesses.size >= GameController.MAX_ATTEMPTS
    val retry = session.last.takeUnless { session.guesses.lastOrNull() == it }
}
