package ast

import printscript.language.token.TokenType

/**
 * StringAST is a class that represents a string in the AST.
 * @param value The value of the string.
 */
data class StringAST(override val value: String, override val type: TokenType = TokenType.STRING_TYPE) : AST, LiteralAST<String> {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
