package AST

import Interpreter.ASTVisitor

interface AST {
    fun accept(visitor: ASTVisitor): AST
}
