package ast


/**
 * AST visitor
 * @param left AST
 * @param right AST
 */
data class SubAST(val left: AST, val right: AST) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
