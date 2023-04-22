package ast

import Type

/**
 * @param value The value of the variable
 */
class BooleanAST(override val value: Boolean, override val type: Type = Type.BOOLEAN) : LiteralAST<Boolean> {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
