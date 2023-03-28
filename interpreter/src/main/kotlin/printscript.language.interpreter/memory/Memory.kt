package printscript.language.interpreter.memory

/**
 * Memory implementation
 *
 */
sealed interface Memory {

    fun put(key: String, value: Any?): Memory
    fun get(key: String): Any?
}
