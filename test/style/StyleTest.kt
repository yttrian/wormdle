package org.yttr.lordle.style

import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.backgroundColor
import kotlinx.css.html
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class StyleTest {
    /**
     * Changes to the built style should result in different hashes.
     * It's probably possible for a collision to occur, but unlikely?
     */
    @Test
    fun `hashes are reasonably different for two different styles`() {
        val styleA = object : Style() {
            override fun CssBuilder.apply() {
                html {
                    backgroundColor = Color.white
                }
            }
        }
        val styleB = object : Style() {
            override fun CssBuilder.apply() {
                html {
                    backgroundColor = Color.black
                }
            }
        }

        assertNotEquals(styleA.contentHash, styleB.contentHash)
    }

    @Test
    fun `builds a simple style`() {
        val style = object : Style() {
            override fun CssBuilder.apply() {
                html {
                    backgroundColor = Color.yellow
                }
            }
        }

        assertEquals("html{background-color:yellow;}", style.content.replace(Regex("[ \n]"), ""))
    }
}
