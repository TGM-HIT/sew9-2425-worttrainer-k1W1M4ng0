package at.gao

import java.util.Random
import kotlin.collections.mutableListOf

class Wordtrainer(
        private var wordList: MutableList<Pair<String, String>> = mutableListOf(defaultPair)
) {
    private var selected = 0
    private val random = Random()

    val statistics = Statistics(0, 0)

    /**
     * Returns the current selected pair, with the first one being the default selection.
     * @return the word and url, as pair
     */
    fun getCurrentPair() = wordList[selected]

    /**
     * Selects a specific word and url, and returns it.
     * @param index the index of the pair
     * @return the word and url, as pair
     */
    fun selectPair(index: Int): Pair<String, String> {
        if (index >= wordList.size || index < 0) {
            throw IllegalArgumentException("$index is out of bounds")
        }

        selected = index
        return getCurrentPair()
    }

    /**
     * Selects a random word and url, and returns it.
     * @return the word and url, as pair
     */
    fun selectRandomPair(): Pair<String, String> {
        selected = random.nextInt(wordList.size)

        return getCurrentPair()
    }

    /**
     * Checks if a word (without leading and trailing whitespace) is equal to the current selected
     * word. Affects statistics.
     * @param word the word
     * @return true
     */
    fun checkSolution(word: String): Boolean {
        val correct = word.trim() == getCurrentPair().first

        if (correct) {
            ++statistics.correctAnswers
        }
        ++statistics.totalAnswers

        return correct
    }

    /** class to store statistics */
    data class Statistics(
            var correctAnswers: Int,
            var totalAnswers: Int,
    )

    companion object {
        val defaultPair = Pair("test", "test")
    }
}
