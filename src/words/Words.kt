package org.yttr.wormdle.words

import com.typesafe.config.ConfigFactory

object Words {
    private val config = ConfigFactory.load().getConfig("wormdle")
    private val infiniteSolutions = InfiniteSolutions(config.getInt("seed"))

    /**
     * All dictionary words and solutions
     */
    private val dictionary: Set<String> by lazy {
        val corpus = Corpus().getWords()
        val solutions = infiniteSolutions.getSolutions().map { it.word }

        corpus + solutions
    }

    /**
     * Check if a word is in the dictionary
     */
    fun inDictionary(word: String): Boolean {
        return dictionary.contains(word)
    }

    /**
     * Get the word of the day
     */
    fun forDay(day: Int) = infiniteSolutions.sequenceSolutions().elementAt(day)

    /**
     * Get the total number of solutions
     */
    fun totalSolutions() = infiniteSolutions.getSolutions().count()
}
