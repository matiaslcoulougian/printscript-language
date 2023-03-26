package AST

import Interpreter.ASTVisitor


 class AssignationAST( private val declaration: DeclarationAST,  private val expression: AST):AST {

     override fun  accept(visitor: ASTVisitor): AST = visitor.visit(this)

     fun getDeclaration(): DeclarationAST = declaration
     fun getExpression(): AST = expression

 }