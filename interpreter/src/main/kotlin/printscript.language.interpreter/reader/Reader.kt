package printscript.language.interpreter.reader

import java.util.* // ktlint-disable no-wildcard-imports

/**
 * Reads a line from the console
 * @property scanner The scanner that reads from the console
 * @property read Reads a line from the console
 * @property read The scanner that reads from the console
 */
class ConsoleReader : Reader {
    private val scanner: Scanner = Scanner(System.`in`)

    /**
     * Reads a line from the console
     */
    override fun read(default: String): String {
        return try {
            scanner.nextLine()
        } catch (e: Exception) {
            println(e)
            default
        }
    }
}

/**
 * Reads a line
 * @property read Reads a line
 */
sealed interface Reader {
    /**
     * Reads a line
     */

    fun read(default: String): String
}
