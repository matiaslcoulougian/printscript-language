package ast.literalAST

import ast.AST
import ast.ASTVisitor

/**
 * NumberAST is a class that represents a number in the AST.
 * @param value The value of the number.
 */
data class NumberAST(override val value: Number) : LiteralAST<Number> {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
