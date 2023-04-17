package printscript.language.interpreter.memory

import Type

/**
 * Memory implementation
 * @param variables The variables of the program
 * @param parent The parent memory
 */
data class BlockMemory(val variables: MutableMap<String, Variable>, private val parent: Memory? = null) : Memory {

    /**
     * Puts a value in the memory
     */
    override fun set(key: String, value: Variable): Memory {
        if (variables.get(key) !== null) {
            val newMap = variables.toMutableMap()
            newMap.put(key, value)
            return BlockMemory(newMap, parent)
        } else if (parent?.get(key)?.type == Type.UNDEFINED) {
            val newMap = variables.toMutableMap()
            newMap.put(key, value)
            return BlockMemory(newMap, parent)
        }
        return BlockMemory(variables, parent?.set(key, value))
    }

    /**
     * Gets a value from the memory
     */
    override fun get(key: String): Variable = variables[key] ?: parent?.get(key) ?: Variable(value = null, type = Type.UNDEFINED, isConst = false)

    /**
     * Gets the type of variable
     */
    override fun getType(key: String): Type {
        val value = this.get(key)
        return value?.type
    }

    override fun getIsConst(key: String): Boolean {
        val value = this.get(key)
        return value?.isConst ?: false
    }

    override fun getParent(): Memory? = parent
    fun setParent(parent: Memory): BlockMemory = BlockMemory(variables, parent)
}
