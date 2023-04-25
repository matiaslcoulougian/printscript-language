package ast

data class
BlockAST(val statements: List<AST>, val line: Int, val column: Int) : AST {

    constructor(statements: List<AST>) : this(statements, 0, 0)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
