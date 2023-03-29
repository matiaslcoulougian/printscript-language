package ast

/**
 * Represents a division operation.
 * @param left The left operand.
 * @param right The right operand.
 * @constructor Creates a new division operation.
 *
 */
data class DivAST(val right: AST, val left: AST) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
