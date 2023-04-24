package ast

import Type

/**
 * @param value The value of the variable
 */
data class BooleanAST(override val value: Boolean, val line: Int, val column: Int, override val type: Type = Type.BOOLEAN) : LiteralAST<Boolean> {

    constructor(value: Boolean) : this(value, -1, -1, Type.BOOLEAN)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
