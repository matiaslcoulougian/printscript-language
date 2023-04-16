package ast

import memory.BlockMemory

data class BlockAST(val statements: List<AST>, val memory: BlockMemory) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
