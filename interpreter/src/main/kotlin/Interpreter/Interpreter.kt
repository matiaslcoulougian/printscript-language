package Interpreter

import AST.AST

sealed interface Interpreter{
    fun interpret(ast: AST)

}