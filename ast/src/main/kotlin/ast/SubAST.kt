package ast

/**
 * AST visitor
 * @param left AST
 * @param right AST
 */
data class SubAST(val right: AST, val left: AST, val line: Int, val column: Int) : AST {
    constructor(right: AST, left: AST) : this(right, left, -1, -1)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
