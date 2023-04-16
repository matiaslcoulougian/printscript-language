package ast

/**
 * AST interface
 * @param condition AST
 * @param trueSide AST
 * @param falseSide AST
 */
data class IfAST(val condition: AST, val trueSide: AST, val falseSide: AST) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
