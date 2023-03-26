package interpreter

import AST.AST

/**
 * Interpreter Interface
 */
sealed interface Interpreter {
    /**
     * Interpret the AST
     */
    fun interpret(ast: AST)
}
