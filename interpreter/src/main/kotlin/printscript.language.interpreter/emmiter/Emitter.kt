package printscript.language.interpreter.emmiter

/**
 * Emitter that prints to the console
 */
class ConsoleEmitter : Emitter {
    override fun emit(text: String) {
        println(text)
    }
}

/**
 * Emitter that prints
 *
 */
sealed interface Emitter {
    /**
     * Emit the text
     * @param text The text to emit
     */
    fun emit(text: String)
}
