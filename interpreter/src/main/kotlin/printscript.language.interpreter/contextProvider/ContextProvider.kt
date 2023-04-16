package printscript.language.interpreter.contextProvider

import memory.MapMemory
import memory.Memory
import printscript.language.interpreter.emmiter.ConsoleEmitter
import printscript.language.interpreter.emmiter.Emitter
import printscript.language.interpreter.reader.ConsoleReader
import printscript.language.interpreter.reader.Reader

/**
 * Context provider that provides the context for the interpreter
 * @property emit Emits the text
 * @property read Reads the text
 */
sealed interface ContextProvider {

    fun getMemory(): Memory
    fun setMemory(memory: Memory)
    fun emit(text: String)
    fun read(): String
}

/**
 * Context provider that provides the context for the interpreter
 */
class ConsoleContext(
    private var memory: Memory = MapMemory(mutableMapOf()),
    private val emmiter: Emitter = ConsoleEmitter(),
    private val reader: Reader = ConsoleReader(),
) : ContextProvider {

    override fun getMemory(): Memory = memory
    override fun setMemory(memory: Memory) {
        this.memory = memory
    }
    override fun emit(text: String) = emmiter.emit(text)
    override fun read(): String = reader.read()
}
