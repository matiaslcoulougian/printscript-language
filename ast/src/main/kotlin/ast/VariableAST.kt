package ast

/**
 * @param name Variable name
 */
data class VariableAST(val name: String, val line: Int, val column: Int) : AST {
    constructor(name: String) : this(name, -1, -1)
    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
