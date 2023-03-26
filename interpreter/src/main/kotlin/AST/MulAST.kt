package AST

import interpreter.ASTVisitor

/**
 * MulAST is a class that represents a multiplication in the AST.
 * @param left The left operand.
 * @param right The right operand.
 */
data class MulAST(val left: AST, val right: AST): AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
