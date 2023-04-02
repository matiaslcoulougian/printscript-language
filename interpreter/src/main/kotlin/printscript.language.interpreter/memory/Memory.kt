package printscript.language.interpreter.memory

import printscript.language.token.TokenType

/**
 * Memory implementation
 *
 */
sealed interface Memory {

    fun put(key: String, value: Any?): Memory
    fun get(key: String): Any?
    fun getType(name: String): TokenType
}
