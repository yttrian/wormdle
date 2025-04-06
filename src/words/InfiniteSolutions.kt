package org.yttr.wormdle.words

import kotlin.random.Random

class InfiniteSolutions(private val seed: Int, private val name: String = "/words.txt") {
    /**
     * All solutions from [name]
     */
    fun getSolutions(): List<Solution> {
        val resource = requireNotNull(this.javaClass.getResourceAsStream(name)) {
            "Resource $name not found!"
        }

        val solutions = buildList {
            resource.use { stream ->
                stream.bufferedReader().forEachLine { line ->
                    val split = line.split(" ", limit = WORD_LIST_SPLITS)

                    add(Solution(split[0], split[1], split[2]))
                }
            }
        }

        check(solutions.isNotEmpty()) { "No solutions found in resource $name!" }

        return solutions
    }

    /**
     * Infinite sequence of solutions that only repeats after all words are used once
     */
    fun sequenceSolutions(): Sequence<Solution> {
        val solutions = getSolutions()
        val random = Random(seed)

        return generateSequence {
            solutions.shuffled(random)
        }.flatten()
    }

    companion object {
        const val WORD_LIST_SPLITS = 3
    }
}
