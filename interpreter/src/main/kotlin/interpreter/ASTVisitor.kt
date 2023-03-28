package interpreter

import AST.*

/**
 * AST visitor
 *
 */
interface ASTVisitor {
    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(declarationAST: DeclarationAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(assignationAST: AssignationAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(printAST: PrintAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(sumAST: SumAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(subAST: SubAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(divAST: DivAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(mulAST: MulAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(numberAST: NumberAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(stringAST: StringAST): AST

    fun visit(variableAST: VariableAST):AST
}
