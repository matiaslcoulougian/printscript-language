package ast

import Type

/**
 * NumberAST is a class that represents a number in the AST.
 * @param value The value of the number.
 */
data class NumberAST(override val value: Number, val line: Int, val column: Int, override val type: Type = Type.NUMBER) : LiteralAST<Number> {
    constructor(value: Number) : this(value, -1, -1, Type.NUMBER)

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
