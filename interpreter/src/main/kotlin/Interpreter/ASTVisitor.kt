package Interpreter

import AST.*
import BinaryOperationAST

interface ASTVisitor {
    fun visit(declarationAST: DeclarationAST):AST
    fun visit(assignationAST: AssignationAST):AST
    fun visit(printAST: PrintAST):AST
    fun visit(literal: LiteralAST):AST
    fun visit(binaryOperationAST: BinaryOperationAST):AST
}