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
) : AST {

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
