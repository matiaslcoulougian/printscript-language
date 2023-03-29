package ast

/**
 * Assignation AST node.
 * @param declaration DeclarationAST //or VariableAST
 * @param expression AST //NumberAST, StringAST, VariableAST or Operations
 */
class AssignationAST(val declaration: AST, val expression: AST) : AST {

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
