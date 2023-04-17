package ast

import ast.literalAST.BooleanAST
import ast.literalAST.NumberAST
import ast.literalAST.StringAST

/**
 * AST visitor
 *
 */
public interface ASTVisitor {
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

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(booleanAST: BooleanAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(variableAST: VariableAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(blockAST: BlockAST): AST

    /**
     * Accepts a node and returns the result of the visit.
     */
    fun visit(ifAST: IfAST): AST

    fun visit(inputAST: InputAST): AST
}
