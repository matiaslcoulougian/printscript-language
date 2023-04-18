package ast

import Type

data class InputAST(val inputMsg: String, val type: Type) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
