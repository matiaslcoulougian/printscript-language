package ast

/**
 * Represents a division operation.
 * @param left The left operand.
 * @param right The right operand.
 * @constructor Creates a new division operation.
 *
 */
data class DivAST(val right: AST, val left: AST, val line: Int, val column: Int) : AST {
    constructor(right: AST, left: AST) : this(right, left, 0, 0)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
