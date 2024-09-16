package at.gao

import kotlin.collections.mutableListOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WordtrainerTest {
    val wordlist =
            mutableListOf(
                    Pair("word1", "url1"),
                    Pair("word2", "url2"),
            )
    var wordtrainer = Wordtrainer(wordlist)

    @BeforeEach
    fun init() {
        wordtrainer = Wordtrainer(wordlist)
    }

    @Test
    fun `new Wordtrainer With Default Constructor Should Have Default Value`() {
        wordtrainer = Wordtrainer()
        wordtrainer.selectPair(0)

        assertEquals(
                Wordtrainer.defaultPair,
                wordtrainer.getCurrentPair(),
                "default value doesnt match default pair"
        )
    }

    @Test
    fun `default Selection Should Be None`() {
        assertThrows(NoPairSelectedException::class.java) {
            wordtrainer.getCurrentPair()
        }
    }

    @Test
    fun `selectPair Should Return Selected Pair`() {

        assertEquals(
                wordlist[0],
                wordtrainer.selectPair(0),
                "selected pair doesnt return correct pair"
        )

        assertEquals(
                wordlist[1],
                wordtrainer.selectPair(1),
                "selected pair doesnt return correct pair"
        )
    }

    @Test
    fun `selectRandomPair Should Return Pair`() {

        val randomPair = wordtrainer.selectRandomPair()

        assertTrue(wordlist.contains(randomPair), "unknown pair returned")
    }

    @Test
    fun `checkSolution Should Check Correctly`() {
        wordtrainer.selectPair(1)

        assertFalse(wordtrainer.checkSolution(wordlist[0].first), "incorrectly got checked as true")
        assertTrue(wordtrainer.checkSolution(wordlist[1].first), "incorrectly got checked as false")
    }

    @Test
    fun `checkSolution Should Trim`() {
        wordtrainer.selectPair(1)

        assertTrue(
                wordtrainer.checkSolution("  ${wordlist[1].first}   "),
                "incorrectly got checked as false"
        )
    }

    @Test
    fun `checkSolution Should Switch Pair If Guessed Correctly`() {
        wordtrainer.selectPair(0)

        wordtrainer.checkSolution(wordlist[0].first)

        assertThrows(NoPairSelectedException::class.java) {
            wordtrainer.getCurrentPair()
        }
    }

    @Test
    fun `checkSolution Should Not Switch Pair If Guessed Incorrectly`() {
        wordtrainer.selectPair(0)

        wordtrainer.checkSolution(wordlist[1].first)

        assertTrue(wordtrainer.checkSolution(wordlist[0].first), "Pair was switched")
        
    }


    @Test
    fun `statistics Should Get Tracked`() {
        wordtrainer.selectPair(1)

        wordtrainer.checkSolution(wordlist[0].first)
        wordtrainer.checkSolution(wordlist[0].first)
        wordtrainer.checkSolution(wordlist[1].first)

        assertEquals(3, wordtrainer.statistics.totalAnswers)
        assertEquals(1, wordtrainer.statistics.correctAnswers)
    }
}
