package ast.literalAST

import ast.AST
import ast.ASTVisitor

/**
 * StringAST is a class that represents a string in the AST.
 * @param value The value of the string.
 */
data class StringAST(override val value: String) : LiteralAST<String> {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
