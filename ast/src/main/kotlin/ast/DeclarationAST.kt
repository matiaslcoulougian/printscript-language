package ast

import Type

/**
 * AST interface
 */
data class DeclarationAST(
    /**
     * val name: String,
     */
    val name: String,
    /**
     * val value: AST,
     */
    val type: Type,

    val isConst: Boolean = false,
    val line: Int,
    val column: Int,
) : AST {

    constructor(name: String, type: Type, isConst: Boolean = false) : this(name, type, isConst, 0, 0)

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
