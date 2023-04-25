package ast

/**
 * Assignation AST node.
 * @param declaration DeclarationAST //or VariableAST
 * @param expression AST //NumberAST, StringAST, VariableAST or Operations
 */
data class AssignationAST(val declaration: AST, val expression: AST, val line: Int, val column: Int) : AST {
    constructor(declaration: AST, expression: AST) : this(declaration, expression, 0, 0)

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
