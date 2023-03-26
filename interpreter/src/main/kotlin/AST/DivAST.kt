package AST

import interpreter.ASTVisitor

/**
 * Represents a division operation.
 * @param left The left operand.
 * @param right The right operand.
 * @constructor Creates a new division operation.
 *
 */
 data class DivAST(val left: AST, val right: AST) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}