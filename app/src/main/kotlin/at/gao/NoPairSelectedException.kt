package at.gao

/**
 * This exception gets thrown if no pair was selected for a wordtrainer.
 * @author Simon Gao
 * @version 2024-09-22
 */
class NoPairSelectedException : RuntimeException("There is no word+url pair selected!")
