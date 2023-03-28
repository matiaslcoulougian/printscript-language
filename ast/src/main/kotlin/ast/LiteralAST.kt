package ast

/**
 * Literal AST node.
 * @param T Type of the literal
 * @property value The value of the literal
 */
interface LiteralAST<T> {
    val value: T
}