package ast.literalAST

import ast.AST
import ast.ASTVisitor

/**
 * @param value The value of the variable
 */
class BooleanAST(override val value: Boolean) : LiteralAST<Boolean> {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
