package printscript.language.interpreter.interpreter

import Type
import ast.* // ktlint-disable no-wildcard-imports
import ast.literalAST.BooleanAST
import ast.literalAST.LiteralAST
import ast.literalAST.NumberAST
import ast.literalAST.StringAST
import memory.Variable
import printscript.language.interpreter.contextProvider.ConsoleContext

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
    private val contextProvider = ConsoleContext()

    private val memory = contextProvider.getMemory()
    override fun interpret(ast: AST) {
        ast.accept(this)
    }

    /*override fun visit(assignationAST: AssignationAST): AST {
        val declaration = assignationAST.declaration.accept(this)
        val expression = assignationAST.expression.accept(this)
        if ((declaration is DeclarationAST || declaration is VariableAST)) {
            declaration as DeclarationAST
            if (expression is LiteralAST<*>) {
                if (declaration.type === expression.type) {
                    memory = memory.set(declaration.name, expression.value) as MapMemory
                } else {
                    throw Exception("Cannot assign ${expression.type} to ${declaration.type}")
                }
            } else if (expression is VariableAST) {
                if (declaration.type === memory.getType(expression.name)) {
                    memory = memory.set(declaration.name, memory.get(expression.name)) as MapMemory
                } else {
                    throw Exception("Cannot assign ${memory.getType(expression.name)} to ${declaration.type}, types dont match")
                }
            }
        }
        return expression
    }*/

    override fun visit(declarationAST: DeclarationAST): AST = declarationAST
    override fun visit(assignationAST: AssignationAST): AST {
        val declaration = assignationAST.declaration.accept(this)
        val expression = assignationAST.expression.accept(this)
        when (declaration) {
            is DeclarationAST -> {
                addVariable(declaration, expression)
            }

            is VariableAST -> {
                setVariable(declaration, expression)
            }

            else -> {
                throw Exception("Cannot assign $expression to $declaration")
            }
        }
        return assignationAST
    }

    private fun setVariable(variable: VariableAST, expression: AST) {
        if (validateTypes(contextProvider.getMemory().getType(variable.name), expression)) {
            contextProvider.setMemory(contextProvider.getMemory().set(variable.name, Variable(getValue(expression), memory.getIsConst(variable.name), memory.getType(variable.name))))
        }
    }

    private fun addVariable(declaration: DeclarationAST, expression: AST) {
        if (validateTypes(declaration.type, expression)) {
            contextProvider.setMemory(contextProvider.getMemory().set(declaration.name, Variable(getValue(expression), declaration.isConst, declaration.type)))
        } else {
            throw Exception("Cannot assign $expression to ${declaration.type}")
        }
    }

    private fun validateTypes(type: Type, expression: AST): Boolean {
        return when (type) {
            Type.STRING -> expression is StringAST
            Type.NUMBER -> expression is NumberAST
            Type.BOOLEAN -> expression is BooleanAST
            Type.UNDEFINED -> false
        }
    }

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
                NumberAST(rightValue + leftValue)
            }

            leftValue is String && rightValue is String -> {
                StringAST(rightValue + leftValue)
            }
            leftValue is String && rightValue is Number -> {
                StringAST(rightValue + leftValue)
            }
            leftValue is Number && rightValue is String -> {
                StringAST(rightValue + leftValue)
            }
            else -> {
                throw Exception("Cannot sum $rightValue and $leftValue")
            }
        }
    }

    override fun visit(subAST: SubAST): AST {
        val leftValue = getValue(subAST.left.accept(this))
        val rightValue = getValue(subAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(rightValue - leftValue)
            }

            else -> {
                throw Exception("Cannot sum $rightValue and $leftValue")
            }
        }
    }
    override fun visit(divAST: DivAST): AST {
        val leftValue = this.getValue(divAST.left.accept(this))
        val rightValue = this.getValue(divAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(rightValue / leftValue)
            }

            else -> {
                throw Exception("Cannot sum $rightValue and $leftValue")
            }
        }
    }
    override fun visit(mulAST: MulAST): AST {
        val leftValue = this.getValue(mulAST.left.accept(this))
        val rightValue = this.getValue(mulAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> {
                NumberAST(rightValue * leftValue)
            }
            else -> {
                throw Exception("Cannot sum $rightValue and $leftValue")
            }
        }
    }

    override fun visit(stringAST: StringAST): AST = stringAST
    override fun visit(booleanAST: BooleanAST): AST {
        TODO("Not yet implemented")
    }

    /**
     * Visits a variable
     */
    override fun visit(variableAST: VariableAST): AST = variableAST
    override fun visit(blockAST: BlockAST): AST {
        blockAST.statements.forEach { it.accept(this) }
        return blockAST
    }

    override fun visit(ifAST: IfAST): AST {
        TODO("Not yet implemented")
    }

    override fun visit(numberAST: NumberAST): AST = numberAST

    private fun getValue(ast: AST): Any? {
        if (ast is LiteralAST<*>) {
            return ast.value
        } else if (ast is VariableAST) {
            return contextProvider.getMemory().get(ast.name)
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
    fun interpret(ast: AST)
}
