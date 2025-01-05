package org.yttr.lordle.words

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import org.yttr.lordle.game.GameController
import org.yttr.lordle.words.corpora.CorporaBuilder
import kotlin.random.Random

object Words {
    private const val WORD_LIST_SPLITS = 3

    private val logger = LoggerFactory.getLogger(javaClass.simpleName)
    private val config = ConfigFactory.load().getConfig("lordle")
    private val sources by lazy {
        val lines = javaClass.classLoader.getResource("words.txt")?.readText()?.lines()
        lines?.filter { it.isNotBlank() }?.associate {
            val (word, slug, name) = it.split(" ", limit = WORD_LIST_SPLITS)
            word to (slug to name)
        } ?: emptyMap()
    }
    private val wordList by lazy {
        val random = Random(config.getInt("seed"))
        sources.keys.shuffled(random)
    }
    private lateinit var dicitonary: Set<String>

    /**
     * Warm the dictionary
     */
    suspend fun warm() {
        if (!this::dicitonary.isInitialized) {
            dicitonary = CorporaBuilder.buildWordSet(GameController.WORD_LENGTH) + wordList
            logger.info("Warmed the dictionary with ${dicitonary.size} words")
        }
    }

    /**
     * Check if a word is in the dictionary
     */
    suspend fun inDictionary(word: String): Boolean {
        warm()
        return dicitonary.contains(word)
    }

    /**
     * Get the word of the day
     */
    fun forDay(day: Int) = wordList.getOrNull(day)

    /**
     * Get the source of a word
     */
    fun source(word: String) = sources.getValue(word)
}
