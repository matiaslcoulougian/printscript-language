package AST

import Interpreter.ASTVisitor

 class DeclarationAST( private val name: String, private val type:String):AST{

     override fun  accept(visitor: ASTVisitor) =visitor.visit(this)

     fun getName(): String = name
     fun getType(): String = type

 }
