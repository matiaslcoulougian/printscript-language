package printscript.language.interpreter.interpreter

import ast.* // ktlint-disable no-wildcard-imports
import printscript.language.interpreter.contextProvider.ConsoleContext
import printscript.language.interpreter.memory.Memory
import printscript.language.interpreter.memory.MemoryImpl

/**
 * Interpreter implementation.
 *                  The variables of the program.
 *                  The key is the name of the variable.
 *                  The value is the value of the variable.
 *                  The value can be a number or a string.
 *                  The value can be null.
 * @constructor Creates a new interpreter.
 *
 */

class InterpreterImpl : Interpreter {
    private var memory = MemoryImpl(mutableMapOf())
    private val contextProvider = ConsoleContext()

    /**
     * Interprets the AST.
     */
    override fun interpret(ast: AST) {
        ast.accept(this)
    }

    /**
     * Interprets an array of AST.
     */
    override fun interpret(astArray: Array<AST>) {
        astArray.forEach { it.accept(this) }
    }

    /**
     * Gets the memory of the interpreter.
     */
    override fun getMemory(): Memory = memory

    override fun visit(assignationAST: AssignationAST): AST {
        val declaration = assignationAST.declaration.accept(this)
        val expression = assignationAST.expression.accept(this)
        if ((declaration is DeclarationAST || declaration is VariableAST)) {
            declaration as DeclarationAST
            if (expression is LiteralAST<*>) {
                if (declaration.type === expression.type) {
                    memory = memory.put(declaration.name, expression.value) as MemoryImpl
                } else {
                    throw Exception("Cannot assign ${expression.type} to ${declaration.type}")
                }
            } else if (expression is VariableAST) {
                if (declaration.type === memory.getType(expression.name)) {
                    memory = memory.put(declaration.name, memory.get(expression.name)) as MemoryImpl
                } else {
                    throw Exception("Cannot assign ${memory.getType(expression.name)} to ${declaration.type}, types dont match")
                }
            }
        }
        return expression
    }

    override fun visit(declarationAST: DeclarationAST): AST = declarationAST

    override fun visit(printAST: PrintAST): AST {
        val toPrint = getValue(printAST.value.accept(this))
        contextProvider.emit(toPrint.toString())
        return printAST
    }

    override fun visit(sumAST: SumAST): AST {
        val leftValue = getValue(sumAST.left.accept(this))
        val rightValue = getValue(sumAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(leftValue + rightValue)
            }

            leftValue is String && rightValue is String -> {
                StringAST(leftValue + rightValue)
            }
            leftValue is String && rightValue is Number -> {
                StringAST(leftValue + rightValue)
            }
            leftValue is Number && rightValue is String -> {
                StringAST(leftValue + rightValue)
            }
            else -> {
                throw Exception("Cannot sum $leftValue and $rightValue")
            }
        }
    }

    override fun visit(subAST: SubAST): AST {
        val leftValue = getValue(subAST.left.accept(this))
        val rightValue = getValue(subAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(leftValue - rightValue)
            }

            else -> {
                throw Exception("Cannot sum $leftValue and $rightValue")
            }
        }
    }
    override fun visit(divAST: DivAST): AST {
        val leftValue = this.getValue(divAST.left.accept(this))
        val rightValue = this.getValue(divAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(leftValue / rightValue)
            }

            else -> {
                throw Exception("Cannot sum $leftValue and $rightValue")
            }
        }
    }
    override fun visit(mulAST: MulAST): AST {
        val leftValue = this.getValue(mulAST.left.accept(this))
        val rightValue = this.getValue(mulAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(leftValue * rightValue)
            }
            else -> {
                throw Exception("Cannot sum $leftValue and $rightValue")
            }
        }
    }

    override fun visit(stringAST: StringAST): AST = stringAST

    /**
     * Visits a variable
     */
    override fun visit(variableAST: VariableAST): AST = variableAST
    override fun visit(numberAST: NumberAST): AST = numberAST

    private fun getValue(ast: AST): Any? {
        if (ast is LiteralAST<*>) {
            return ast.value
        } else if (ast is VariableAST) {
            return memory.get(ast.name)
        }
        return null
    }
}

private operator fun Number.minus(number: Number): Number = this.toDouble() - number.toDouble()
private operator fun Number.div(number: Number): Number = this.toDouble() / number.toDouble()
private operator fun Number.plus(number: Number): Number = this.toDouble() + number.toDouble()
private operator fun Number.plus(string: String): String = "$this" + string
private operator fun String.plus(string: String): String = this + string
private operator fun String.plus(number: Number): String = this + "$number"

private operator fun Number.times(number: Number): Number = this.toDouble() * number.toDouble()

/**
 * Interpreter Interface
 */
sealed interface Interpreter : ASTVisitor {
    /**
     * Interpret the AST
     * @param ast The AST to interpret.
     *
     */
    fun interpret(ast: AST)

    /**
     * Interpret an array of AST
     */
    fun interpret(astArray: Array<AST>)

    /**
     * Gets the memory of the interpreter.
     */
    fun getMemory(): Memory
}
