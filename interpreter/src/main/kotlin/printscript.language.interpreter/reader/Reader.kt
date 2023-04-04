package printscript.language.interpreter.reader

import java.util.*

/**
 * Reads a line from the console
 * @property scanner The scanner that reads from the console
 * @property read Reads a line from the console
 * @property read The scanner that reads from the console
 */
class ConsoleReader : Reader {
    val scanner = Scanner(System.`in`)

    /**
     * Reads a line from the console
     */
    override fun read(): String = scanner.nextLine()
}

/**
 * Reads a line
 * @property read Reads a line
 */
sealed interface Reader {
    /**
     * Reads a line
     */
    fun read(): String
}
