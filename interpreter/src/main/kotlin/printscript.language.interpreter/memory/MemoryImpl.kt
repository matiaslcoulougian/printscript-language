package printscript.language.interpreter.memory



/**
  * Memory implementation
  * @param variables The variables of the program
  *
  */
 data class MemoryImpl(val variables: MutableMap<String, Any>): Memory {
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
 }


