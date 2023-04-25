package ast

import Type

/**
 * StringAST is a class that represents a string in the AST.
 * @param value The value of the string.
 */
data class StringAST(override val value: String, val line: Int, val column: Int, override val type: Type = Type.STRING) : LiteralAST<String> {
    constructor(value: String) : this(value, 0, 0, Type.STRING)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
