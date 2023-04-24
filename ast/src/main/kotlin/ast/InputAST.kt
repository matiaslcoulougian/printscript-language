package ast

import Type

data class InputAST(val inputMsg: AST, val type: Type, val line: Int, val column: Int) : AST {
    constructor(inputMsg: AST, type: Type) : this(inputMsg, type, -1, -1)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
