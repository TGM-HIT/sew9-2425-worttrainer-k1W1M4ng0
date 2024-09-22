package at.gao

import java.io.File
import java.net.URL
import org.json.*

/**
 * This class is a strategy to read/write a wordtrainer as json file
 * @author Simon Gao
 * @version 2024-09-22
 */
class JsonFileReadWriter(val filename: String) : ReadWriter {

    override fun saveTo(wordtrainer: Wordtrainer) {
        val json = JSONObject(wordtrainer)
        val text = json.toString(2)

        File(filename).writeText(text)
    }

    override fun loadFrom(wordtrainer: Wordtrainer) {
        val file = File(filename)
        if (file.canRead()) {

            val text = file.readText()

            try {
                // parse json file
                val newTrainer = JSONObject(text)

                // statistics
                val correct = newTrainer.getJSONObject("statistics").getInt("correctAnswers")
                val total = newTrainer.getJSONObject("statistics").getInt("totalAnswers")
                // word list
                val jsonList = newTrainer.optJSONArray("wordList", JSONArray())
                val list = mutableListOf<Pair<String, URL>>()
                for (i in 0 until jsonList.length()) {
                    val item = jsonList.getJSONObject(i)
                    val word = item.getString("first")
                    val url = URL(item.getString("second"))

                    list.add(Pair(word, url))
                }

                // save to the wordtrainer
                wordtrainer.statistics.correctAnswers = correct
                wordtrainer.statistics.totalAnswers = total
                wordtrainer.wordList = list

                println("successfully read wordtrainer:")
                println("correct: $correct, total: $total")
                println("words: $list")
            } catch (e: JSONException) {
                println("Error while parsing file: ${e.message}")
            }
        }
    }
}
