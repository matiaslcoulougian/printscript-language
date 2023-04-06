package printscript.language.interpreter.contextProvider

import printscript.language.interpreter.emmiter.ConsoleEmitter
import printscript.language.interpreter.memory.Memory
import printscript.language.interpreter.memory.MemoryImpl
import printscript.language.interpreter.reader.ConsoleReader

/**
 * Context provider that provides the context for the interpreter
 * @property emit Emits the text
 * @property read Reads the text
 */
sealed interface ContextProvider {
    /**
     * Gets the memory of the interpreter
     */
    fun getMemory(): Memory

    /**
     * Emits the text
     */
    fun emit(text: String)

    /**
     * Reads the text
     */
    fun read(): String
}

/**
 * Context provider that provides the context for the interpreter
 */
class ConsoleContext : ContextProvider {
    private var memory = MemoryImpl(mutableMapOf())
    private val emmiter = ConsoleEmitter()
    private val reader = ConsoleReader()
    override fun getMemory(): Memory = memory
    override fun emit(text: String) = emmiter.emit(text)
    override fun read(): String = reader.read()
}
