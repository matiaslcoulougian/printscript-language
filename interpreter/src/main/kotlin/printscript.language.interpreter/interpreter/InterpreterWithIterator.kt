package printscript.language.interpreter.interpreter

import ast.AST

class InterpreterWithIterator(
    private val interpreter: Interpreter,
    private val astIterator: Iterator<AST?>,
) {
    fun interpretNextAST() {
        val nextAST = astIterator.next()
        if (nextAST != null) {
            interpreter.interpret(nextAST)
        }
    }
    fun hasNextInterpretation(): Boolean = astIterator.hasNext()
}
