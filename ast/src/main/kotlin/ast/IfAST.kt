package ast

/**
 * AST interface
 * @param condition AST
 * @param ifBlock AST
 * @param elseBlock AST
 */
data class IfAST(val condition: AST, val ifBlock: BlockAST, val elseBlock: BlockAST? = null) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
