package ast

class InputAST : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
