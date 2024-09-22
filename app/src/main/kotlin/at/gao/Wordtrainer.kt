package at.gao

import java.net.URL
import java.util.Random
import kotlin.collections.mutableListOf
import org.json.*

/**
 * This class is a model class for a game of wordtrainer. It contains a list of word+url pairs, and
 * allows a random or manual selection of one pair. Also tracks statistics that get updated after
 * each guess.
 * @author Simon Gao
 * @version 2024-09-22
 */
class Wordtrainer(var wordList: MutableList<Pair<String, URL>> = mutableListOf(defaultPair)) {
    private var selected = -1
    private val random = Random()

    val statistics = Statistics(0, 0)

    /**
     * Returns the current selected pair, or throws an exception if nothing is selected
     * @return the word and url, as pair
     */
    @JSONPropertyIgnore
    fun getCurrentPair(): Pair<String, URL> {
        if (selected < 0) throw NoPairSelectedException()

        return wordList[selected]
    }

    /**
     * Selects a specific word and url, and returns it.
     * @param index the index of the pair
     * @return the word and url, as pair
     */
    fun selectPair(index: Int): Pair<String, URL> {
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
    fun selectRandomPair(): Pair<String, URL> {
        selected = random.nextInt(wordList.size)

        return getCurrentPair()
    }

    /**
     * Checks if a word (without leading and trailing whitespace) is equal to the current selected
     * word. If the guess is correct, the selection is cleared. Affects statistics.
     * @param word the word
     * @return true
     */
    fun checkSolution(word: String): Boolean {
        val correct = word.trim() == getCurrentPair().first

        if (correct) {
            ++statistics.correctAnswers

            // clear selection
            selected = -1
        }
        ++statistics.totalAnswers

        return correct
    }

    /**
     * Saves this wordtrainer with a specified strategy.
     * @param strategy the strategy to use
     */
    fun saveTo(strategy: ReadWriter) {
        strategy.saveTo(this)
    }

    /**
     * Loads this wordtrainer with a specified strategy.
     * @param strategy the strategy to use
     */
    fun loadFrom(strategy: ReadWriter) {
        strategy.loadFrom(this)
    }

    /** class to store statistics */
    data class Statistics(
            var correctAnswers: Int,
            var totalAnswers: Int,
    )

    companion object {
        val defaultPair = Pair("test", URL("https://picsum.photos/200"))
    }
}
