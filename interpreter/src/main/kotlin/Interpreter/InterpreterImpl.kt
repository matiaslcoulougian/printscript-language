package Interpreter

import AST.*
import BinaryOperationAST
import AST.AST as AST

class InterpreterImpl:Interpreter, ASTVisitor {
    val variables = mutableMapOf<String,Any>();

    override fun interpret(ast: AST) {
            ast.accept(this)
    }

    override fun visit(declarationAST: DeclarationAST) = declarationAST
    override fun visit(assignationAST: AssignationAST): AST {
        val declaration = assignationAST.getDeclaration().accept(this)
        val expression = assignationAST.getExpression().accept(this)
        if (declaration is DeclarationAST && expression is LiteralAST)
        variables.put(declaration.getName(),expression.getValue())
        return  assignationAST
    }

    override fun visit(literal: LiteralAST): AST = literal

    override fun visit(binaryOperationAST: BinaryOperationAST): AST {
        TODO("Not yet implemented")
    }


    override fun visit(printAST: PrintAST): AST {
        println(printAST.accept(this))
        return printAST
    }


}