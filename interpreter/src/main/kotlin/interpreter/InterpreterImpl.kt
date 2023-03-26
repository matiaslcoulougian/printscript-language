package interpreter

import AST.*
import AST.AST as AST

/**
 * Interpreter implementation.
 *                  The variables of the program.
 *                  The key is the name of the variable.
 *                  The value is the value of the variable.
 *                  The value can be a number or a string.
 *                  The value can be null.
 * @constructor Creates a new interpreter.
 * @property variables The variables of the program.
 *
 */
class InterpreterImpl:Interpreter, ASTVisitor {
     private val variables = mutableMapOf<String, Any>()


    /**
     * Interprets the AST.
     */
    override fun interpret(ast: AST) {
        ast.accept(this)
    }

    override fun visit(declarationAST: DeclarationAST): DeclarationAST = declarationAST
    override fun visit(assignationAST: AssignationAST): AST {
        val declaration = assignationAST.declaration.accept(this)
        val expression = assignationAST.expression.accept(this)
        if (declaration is DeclarationAST && (expression is LiteralAST<*>))
            expression.value?.let { variables.put(declaration.name, it) }
        return assignationAST
    }

    override fun visit(printAST: PrintAST): AST {
        println(printAST.accept(this))
        return printAST
    }

    override fun visit(sumAST: SumAST): AST {
        val left = sumAST.left.accept(this)
        val right = sumAST.right.accept(this)
        if (left is LiteralAST<*> && right is LiteralAST<*>){
            return when {
                left.value is Number && right.value is Number -> {
                    NumberAST(left.value as Number + right.value as Number)
                }

                left.value is String && right.value is String -> {
                    StringAST(left.value as String + right.value as String)
                }

                else -> {
                    throw Exception("Cannot sum ${left.value} and ${right.value}")
                }
            }
        }
        else throw Exception("Cannot sum ${left} and ${right}")
    }

    override fun visit(subAST: SubAST): AST {
        val left = subAST.left.accept(this)
        val right = subAST.right.accept(this)
        if (left is LiteralAST<*> && right is LiteralAST<*>){
            return when {
                left.value is Number && right.value is Number -> {
                    NumberAST(left.value as Number - right.value as Number)
                }
                else -> {
                    throw Exception("Cannot sum ${left.value} and ${right.value}")
                }
            }
        }
        throw Exception("Cannot sum ${left} and ${right}")
    }

    override fun visit(divAST: DivAST): AST {
        val left = divAST.left.accept(this)
        val right = divAST.right.accept(this)
        if (left is LiteralAST<*> && right is LiteralAST<*>){
            return when {
                left.value is Number && right.value is Number -> {
                    NumberAST(left.value as Number / right.value as Number)
                }
                else -> {
                    throw Exception("Cannot sum ${left.value} and ${right.value}")
                }
            }
        }
        throw Exception("Cannot sum ${left} and ${right}")    }

    override fun visit(stringAST: StringAST): AST = stringAST

    override fun visit(numberAST: NumberAST): AST = numberAST

    override fun visit(mulAST: MulAST): AST {
        val left = mulAST.left.accept(this)
        val right = mulAST.right.accept(this)
        if (left is LiteralAST<*> && right is LiteralAST<*>){
            return when {
                left.value is Number && right.value is Number -> {
                    NumberAST(left.value as Number * right.value as Number)
                }
                else -> {
                    throw Exception("Cannot sum ${left.value} and ${right.value}")
                }
            }
        }
        throw Exception("Cannot sum ${left} and ${right}")
    }

    }

private operator fun Number.minus(number: Number): Number = this.toDouble() - number.toDouble()
private operator fun Number.div(number: Number): Number = this.toDouble() / number.toDouble()
private operator fun Number.plus(number: Number): Number = this.toDouble() + number.toDouble()
private operator fun Number.times(number: Number): Number = this.toDouble() * number.toDouble()
