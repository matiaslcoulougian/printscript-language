package printscript.language.interpreter.memory

import printscript.language.token.TokenType

/**
 * Memory implementation
 * @param variables The variables of the program
 *
 */
data class MemoryImpl(val variables: MutableMap<String, Any>) : Memory {
    /**
     * Puts a value in the memory
     */
    override fun put(key: String, value: Any?): Memory {
        val newMap = variables.toMutableMap()
        newMap[key] = value as Any
        return MemoryImpl(newMap)
    }

    /**
     * Gets a value from the memory
     */
    override fun get(key: String): Any? = variables[key]
    /**
     * Gets the type of variable
     */
    override fun getType(name: String): TokenType {
        val value = variables[name]
        return when (value) {
            is Number -> TokenType.NUMBER_TYPE
            is String -> TokenType.STRING_TYPE
            else -> throw Exception("Type not found for $name")

        }
    }
}
