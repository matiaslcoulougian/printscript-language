package ast

/**
 * AST visitor
 * @param left AST
 * @param right AST
 */
data class SumAST(val right: AST, val left: AST) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
