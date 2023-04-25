package ast

import Type

data class LiteralInputAST(override val value: String, val line: Int, val column: Int, override val type: Type = Type.INPUT) : LiteralAST<String> {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
