package AST

import Interpreter.ASTVisitor

class PrintAST:AST {

    override fun accept(visitor: ASTVisitor) = visitor.visit(this)


    fun getToPrint(): Any {
        return "string"
    }
}