import AST.AST
import AST.Operation
import Interpreter.ASTVisitor

class BinaryOperationAST(private val operation: Operation,private val right:AST,private val left:AST ): AST{
    override fun  accept(visitor: ASTVisitor): AST {
        return visitor.visit(this)
    }
    fun getOperation(): Operation = operation
    fun getRight(): AST = right
    fun getLeft(): AST = left

}