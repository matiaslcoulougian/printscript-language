package ast

/**
 * Assignation AST node.
 * @param declaration DeclarationAST
 * @param expression AST
 */
class AssignationAST(val declaration: AST, val expression: AST) : AST {

    override fun accept(visitor: ASTVisitor): AST = visitor.visit(this)
}
