package ast

/**
 * @param value AST value to Print
 */

data class PrintAST(val value: AST, val line: Int, val column: Int) : AST {
    constructor(value: AST) : this(value, 0, 0)

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
