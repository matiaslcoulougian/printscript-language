package AST

import interpreter.ASTVisitor

/**
 * @param value AST value to Print
 */
 data class PrintAST(val value:AST):AST {

    override fun accept(visitor: ASTVisitor):AST = visitor.visit(this)


}