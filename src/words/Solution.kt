package org.yttr.wormdle.words

/**
 * The [word] is guessed and links to a lore entry with the provided [name] and [slug]
 */
data class Solution(val word: String, val slug: String, val name: String)

fun Solution.link() = "https://www.ishtar-collective.net/$slug?highlight=$word"
