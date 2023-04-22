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
    private fun readNumber(): Number = scanner.nextLine().toDouble()

    private fun readString(): String = scanner.nextLine()

    private fun readBoolean(): Boolean = scanner.nextLine().toBoolean()

    override fun <T> read(default: T): T {
        val value: T = when (default) {
            is Number -> readNumber() as T
            is String -> readString() as T
            is Boolean -> readBoolean() as T
            else -> throw IllegalArgumentException("Unsupported type: $default")
        }
        return try {
            value
        } catch (e: Exception) {
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

    fun <T> read(default: T): T
}
