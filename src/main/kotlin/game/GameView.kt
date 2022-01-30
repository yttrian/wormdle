package org.yttr.lordle.game

import kotlinx.html.h1
import org.yttr.lordle.mvc.LayoutTemplate
import org.yttr.lordle.mvc.View

object GameView : View<GameModel> {
    override fun LayoutTemplate.apply(model: GameModel) {
        content {
            h1 {
                +model.word
            }
        }
    }
}
