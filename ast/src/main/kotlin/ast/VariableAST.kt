package ast


/**
 * @param name Variable name
 */
data class VariableAST(val name: String) : AST {
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}