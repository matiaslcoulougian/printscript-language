package memory

import Type

/**
 * Memory implementation
 * @param variables The variables of the program
 *
 */
data class MapMemory(val variables: MutableMap<String, Variable?>) : Memory {
    /**
     * Puts a value in the memory
     */
    override fun set(key: String, value: Variable): Memory {
        val newMap = variables.toMutableMap()
        newMap[key] = value as Variable
        return MapMemory(newMap)
    }

    /**
     * Gets a value from the memory
     */
    override fun get(key: String): Variable = variables[key] ?: Variable(value = null, type = Type.UNDEFINED, isConst = false)
    override fun getType(key: String): Type {
        val value = this.get(key)
        return value.type
    }

    override fun getIsConst(key: String): Boolean {
        val value = this.get(key)
        return value.isConst }

    /**
     * Gets the type of variable
     */
}
