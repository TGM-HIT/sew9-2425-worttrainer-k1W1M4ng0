package at.gao

import java.awt.BorderLayout
import java.awt.Image
import java.awt.Dimension
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField
import javax.imageio.ImageIO

/**
 * This class is an implementation of the ui with JOptionPane.
 * @author Simon Gao
 * @version 2024-09-22
 */
class JOptionPaneUI(wordtrainer: Wordtrainer, val filename: String?) : Userinterface(wordtrainer) {

    init {
        if (filename != null) {
            wordtrainer.loadFrom(JsonFileReadWriter(filename))
        }
    }

    override fun start() {
        var running = true

        var guessedCorrectly = true

        while (running) {
            // select random pair on start or if guessed correctly
            if (guessedCorrectly) {
                wordtrainer.selectRandomPair()
            }

            // TODO debug print
            println("word is ${wordtrainer.getCurrentPair()}")

            // show dialog
            val input = showDialogAndGetInput(guessedCorrectly)

            if (input.trim() == "") {
                running = false
            } else {
                guessedCorrectly = wordtrainer.checkSolution(input)
            }
        }

        // save the wordtrainer
        if(filename != null) {
            wordtrainer.saveTo(JsonFileReadWriter(filename))
        }
    }

    private fun showDialogAndGetInput(guessedCorrectly: Boolean): String {

        val panel = JPanel()
        panel.layout = BorderLayout()

        // show statistics
        val correct = wordtrainer.statistics.correctAnswers
        val total = wordtrainer.statistics.totalAnswers
        val wrong = total - correct
        val statisticsString =
                if (guessedCorrectly) {

                    "Correct: $correct, Wrong: $wrong, Total: $total"
                } else {
                    "Wrong!  Correct: $correct, Wrong: $wrong, Total: $total"
                }
        panel.add(JLabel(statisticsString), BorderLayout.PAGE_START)

        // show picture
        var image: Image = ImageIO.read(wordtrainer.getCurrentPair().second)
        // scale to size
        image = image.getScaledInstance(600, 300, Image.SCALE_DEFAULT) // stretch c:
        panel.add(JLabel(ImageIcon(image)), BorderLayout.CENTER)

        // input field
        val inputField = JTextField()
        panel.add(inputField, BorderLayout.PAGE_END)

        // show message
        JOptionPane.showMessageDialog(null, panel)

        return inputField.text
    }
}
