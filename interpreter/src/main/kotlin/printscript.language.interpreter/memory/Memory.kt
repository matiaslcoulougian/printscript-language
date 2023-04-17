package printscript.language.interpreter.memory

import Type

/**
 * Memory implementation
 *
 */
sealed interface Memory {

    fun set(key: String, value: Variable): Memory
    fun get(key: String): Variable

    fun getParent(): Memory?

    fun getType(key: String): Type
    fun getIsConst(key: String): Boolean
}
