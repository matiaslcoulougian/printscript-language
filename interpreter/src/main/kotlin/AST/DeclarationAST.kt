package AST

import interpreter.ASTVisitor



 data class DeclarationAST(
     /**
      * val name: String,
      */
     val name: String,
     /**
      * val value: AST,
      */
     val type:String):AST{

     override fun  accept(visitor: ASTVisitor): AST =visitor.visit(this)
     

 }
