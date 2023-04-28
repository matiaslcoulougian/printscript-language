package ast

import Type

/**
 * NumberAST is a class that represents a number in the AST.
 * @param value The value of the number.
 */
data class NumberAST(override val value: Double, val line: Int, val column: Int, override val type: Type = Type.NUMBER) : LiteralAST<Double> {
    constructor(value: Double) : this(value, 0, 0, Type.NUMBER)

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)

    override fun getStringValue(): String = if (isInt()) "${value.toInt()}" else "$value"

    private fun isInt() = value % 1 == 0.0
}
