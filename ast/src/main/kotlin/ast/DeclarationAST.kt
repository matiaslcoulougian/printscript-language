package ast

import printscript.language.token.TokenType

data class DeclarationAST(
    /**
     * val name: String,
     */
    val name: String,
    /**
     * val value: AST,
     */
    val type: TokenType,
    val isConstant: Boolean = true,
) : AST {

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
