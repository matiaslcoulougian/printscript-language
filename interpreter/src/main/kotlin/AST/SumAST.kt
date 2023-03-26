package AST

import interpreter.ASTVisitor

/**
 * AST visitor
 * @param left AST
 * @param right AST
 */
data class SumAST(val left: AST, val right: AST): AST {
     override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}