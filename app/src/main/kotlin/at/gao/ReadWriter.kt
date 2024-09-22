package at.gao

/**
 * This interface allows using strategies to save/load a Wordtrainer.
 * @author Simon Gao
 * @version 2024-09-22
 */
interface ReadWriter {
    /**
     * Saves a Wordtrainer object.
     * @param wordtrainer the wordtrainer to save
     */
    fun saveTo(wordtrainer: Wordtrainer)
    /**
     * Loads a Wordtrainer object and saves it into the object passed as argument.
     * @param wordtrainer the wordtrainer to save to
     */
    fun loadFrom(wordtrainer: Wordtrainer)
}
