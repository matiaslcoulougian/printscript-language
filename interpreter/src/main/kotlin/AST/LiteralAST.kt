package AST

import Interpreter.ASTVisitor

class LiteralAST( private val value:Any):AST {
    override fun accept(visitor: ASTVisitor) = visitor.visit(this)

    fun getValue():Any = value


}