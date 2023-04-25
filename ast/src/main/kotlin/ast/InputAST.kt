package ast

data class InputAST(val inputMsg: AST, val line: Int, val column: Int) : AST {
    constructor(inputMsg: AST) : this(inputMsg, -1, -1)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
