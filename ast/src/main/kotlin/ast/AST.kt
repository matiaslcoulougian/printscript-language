package ast

/**
 * AST interface
 */
public interface AST {

    /**
     * Accepts a visitor and returns the result of the visit.
     */
    fun accept(visitor: ASTVisitor): AST
}
