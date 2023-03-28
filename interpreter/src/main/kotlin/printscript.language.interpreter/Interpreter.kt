package printscript.language.interpreter

import ast.*
import printscript.language.interpreter.Interpreter
import printscript.language.interpreter.memory.MemoryImpl

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
class InterpreterImpl : Interpreter, ASTVisitor {
    private val memory = MemoryImpl(mutableMapOf())

    /**
     * Interprets the AST.
     */
    override fun interpret(ast: AST) {
        ast.accept(this)
    }


    override fun visit(assignationAST: AssignationAST): AST {
        var declaration = assignationAST.declaration.accept(this)
        val expression = assignationAST.expression.accept(this)
        if ((declaration is DeclarationAST || declaration is VariableAST)) {
            declaration as DeclarationAST
            if (expression is LiteralAST<*>)
            memory.put(declaration.name, expression.value)
            else if (expression is VariableAST) {
                memory.put(declaration.name, memory.get(expression.name))
            }
        }
        return assignationAST
    }


    override fun visit(declarationAST: DeclarationAST): AST = declarationAST


    override fun visit(printAST: PrintAST): AST {
        val toPrint = getValue(printAST.value.accept(this))
        println(toPrint)

        return printAST
    }


    override fun visit(sumAST: SumAST): AST {
        val leftValue = getValue(sumAST.left.accept(this))
        val rightValue = getValue(sumAST.right.accept(this))
            return when {
               leftValue is Number && rightValue is Number -> {
                   NumberAST(rightValue + rightValue)
                }

               leftValue is String && rightValue is String -> {
                   StringAST(rightValue + rightValue)
                }

                else -> {
                    throw Exception("Cannot sum $rightValue and $rightValue")
                }
            }
    }

    override fun visit(subAST: SubAST): AST {
        val leftValue = getValue(subAST.left.accept(this))
        val rightValue = getValue(subAST.right.accept(this))
            return when {
               leftValue is Number && rightValue is Number -> {
                   NumberAST(rightValue - rightValue)
                }

                else -> {
                    throw Exception("Cannot sum $rightValue and $rightValue")
                }
            }

    }
    override fun visit(divAST: DivAST): AST {
        val leftValue = this.getValue(divAST.left.accept(this))
        val rightValue = this.getValue(divAST.right.accept(this))
        return when {
               leftValue is Number && rightValue is Number -> {
                   NumberAST(rightValue / rightValue)
                }

                else -> {
                    throw Exception("Cannot sum $rightValue and $rightValue")
                }
            }
    }
    override fun visit(mulAST: MulAST): AST {
        val leftValue = this.getValue(mulAST.left.accept(this))
        val rightValue = this.getValue(mulAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(rightValue * rightValue)
            }
            else -> {
                throw Exception("Cannot sum $rightValue and $rightValue")
            }
        }
    }

    override fun visit(stringAST: StringAST): AST = stringAST
    override fun visit(variableAST: VariableAST): AST = variableAST

    override fun visit(numberAST: NumberAST): AST = numberAST


    private fun getValue(ast: AST): Any? {
        if (ast is LiteralAST<*>) {
            return ast.value
        }else if(ast is VariableAST){
            return memory.get(ast.name)
        }
        return null
    }
}

private operator fun Number.minus(number: Number): Number = this.toDouble() - number.toDouble()
private operator fun Number.div(number: Number): Number = this.toDouble() / number.toDouble()
private operator fun Number.plus(number: Number): Number = this.toDouble() + number.toDouble()
private operator fun Number.times(number: Number): Number = this.toDouble() * number.toDouble()

/**
 * Interpreter Interface
 */
sealed interface Interpreter {
    /**
     * Interpret the AST
     */
    fun interpret(ast: AST)
}