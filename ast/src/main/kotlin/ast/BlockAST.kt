package ast

data class BlockAST(val statements: List<AST>) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
