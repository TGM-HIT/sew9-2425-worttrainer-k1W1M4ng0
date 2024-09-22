package at.gao

/**
 * This class allows creating user interfaces to work with a Wordtrainer.
 * @author Simon Gao
 * @version 2024-09-22
 */
abstract class Userinterface(val wordtrainer: Wordtrainer) {
    /** 
     * Starts the ui.
     */
    abstract fun start()
}
