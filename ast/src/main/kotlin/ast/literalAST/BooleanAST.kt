package ast.literalAST

import Type
import ast.AST
import ast.ASTVisitor

/**
 * @param value The value of the variable
 */
class BooleanAST(override val value: Boolean, override val type: Type = Type.BOOLEAN) : LiteralAST<Boolean> {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
