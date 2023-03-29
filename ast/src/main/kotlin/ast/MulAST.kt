package ast

/**
 * MulAST is a class that represents a multiplication in the AST.
 * @param left The left operand.
 * @param right The right operand.
 */
data class MulAST(val right: AST, val left: AST) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
