/*
 * This source file was generated by the Gradle 'init' task
 */
package at.gao

fun main() {
    println("Enter a filename (or empty to skip):")

    val filename = readlnOrNull()

    JOptionPaneUI(Wordtrainer(), filename).start()
}
