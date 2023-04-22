package ast

/**
 * AST interface
 * @param condition AST
 * @param ifBlock AST
 * @param elseBlock AST
 */
data class IfAST(val condition: AST, val ifBlock: BlockAST, val elseBlock: BlockAST?) : AST {
    constructor(condition: AST, ifBlock: BlockAST) : this(condition, ifBlock, null)

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
