package ast.literalAST

import ast.AST

/**
 * Literal AST node.
 * @param T Type of the literal
 * @property value The value of the literal
 */
sealed interface LiteralAST<T> : AST {
    val value: T
}
