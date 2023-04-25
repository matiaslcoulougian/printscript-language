package ast

/**
 * AST interface
 * @param condition AST
 * @param ifBlock AST
 * @param elseBlock AST
 */
data class IfAST(val condition: AST, val ifBlock: BlockAST, val elseBlock: BlockAST?, val line: Int, val column: Int) : AST {
    constructor(condition: AST, ifBlock: BlockAST, line: Int, column: Int) : this(condition, ifBlock, null, line, column)
    constructor(condition: AST, ifBlock: BlockAST, elseBlock: BlockAST) : this(condition, ifBlock, elseBlock, 0, 0)
    constructor(condition: AST, ifBlock: BlockAST) : this(condition, ifBlock, null, 0, 0)

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
