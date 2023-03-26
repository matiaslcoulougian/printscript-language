package AST

import interpreter.ASTVisitor


 /**
  * Assignation AST node.
  * @param declaration DeclarationAST
  * @param expression AST
  */
 class AssignationAST( val declaration: DeclarationAST, val expression: AST):AST {

     override fun  accept(visitor: ASTVisitor): AST = visitor.visit(this)



 }