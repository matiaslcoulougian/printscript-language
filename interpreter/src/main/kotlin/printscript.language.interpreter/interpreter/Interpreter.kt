package printscript.language.interpreter.interpreter

import Type
import ast.* // ktlint-disable no-wildcard-imports
import ast.literalAST.BooleanAST
import ast.literalAST.LiteralAST
import ast.literalAST.NumberAST
import ast.literalAST.StringAST
import printscript.language.interpreter.contextProvider.ContextProvider
import printscript.language.interpreter.memory.BlockMemory
import printscript.language.interpreter.memory.Memory
import printscript.language.interpreter.memory.Variable

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

class InterpreterImpl(private val contextProvider: ContextProvider) : Interpreter {

    override fun interpret(ast: AST) {
        ast.accept(this)
    }

    override fun getMemory(): Memory = contextProvider.getMemory()

    private fun setMemory(memory: Memory) = contextProvider.setMemory(memory)

    private fun getType(ast: AST): Type {
        if (ast is VariableAST) {
            return getMemory().getType(ast.name)
        } else if (ast is LiteralAST<*>) return ast.type
        return Type.UNDEFINED
    }
    private fun isConst(variable: VariableAST): Boolean = getMemory().getIsConst(variable.name)

    private fun getValue(ast: AST): Any? {
        if (ast is LiteralAST<*>) {
            return ast.value
        } else if (ast is VariableAST) return getMemory().get(ast.name).value
        return null
    }

    override fun visit(declarationAST: DeclarationAST): AST = declarationAST
    override fun visit(assignationAST: AssignationAST): AST {
        val declaration = assignationAST.declaration.accept(this)
        when (val expression = assignationAST.expression.accept(this)) {
            is LiteralAST<*> -> when (declaration) {
                is DeclarationAST -> addVariable(declaration, expression)

                is VariableAST -> setVariable(declaration, expression)

                else -> throw Exception("Cannot assign $expression to $declaration")
            }
        }

        return assignationAST
    }

    private fun setVariable(variable: VariableAST, expression: LiteralAST<*>) {
        if (validateTypes(getType(variable), expression) && !isConst(variable)) {
            setMemory(
                getMemory().set(
                    variable.name,
                    Variable(
                        this.getValue(expression),
                        isConst(variable),
                        getType(variable),
                    ),
                ),
            )
        } else {
            throw Exception("Cannot assign $expression to ${variable.name}")
        }
    }

    private fun addVariable(declaration: DeclarationAST, expression: LiteralAST<*>) {
        if (validateTypes(declaration.type, expression)) {
            setMemory(
                getMemory().set(
                    declaration.name,
                    Variable(
                        this.getValue(expression),
                        declaration.isConst,
                        declaration.type,
                    ),
                ),
            )
        } else {
            throw Exception("Cannot assign $expression to ${declaration.type}")
        }
    }

    private fun validateTypes(type: Type, expression: AST): Boolean = type == getType(expression)

    override fun visit(printAST: PrintAST): AST {
        val toPrint = this.getValue(printAST.value.accept(this))
        contextProvider.emit(toPrint.toString())
        return printAST
    }

    override fun visit(sumAST: SumAST): AST {
        val leftValue = this.getValue(sumAST.left.accept(this))
        val rightValue = this.getValue(sumAST.right.accept(this))

        return when {
            leftValue is Number && rightValue is Number -> NumberAST(rightValue + leftValue)

            leftValue is String && rightValue is String -> StringAST(rightValue + leftValue)

            leftValue is String && rightValue is Number -> StringAST(rightValue + leftValue)

            leftValue is Number && rightValue is String -> StringAST(rightValue + leftValue)

            else -> throw Exception("Cannot sum $rightValue and $leftValue")
        }
    }

    override fun visit(subAST: SubAST): AST {
        val leftValue = this.getValue(subAST.left.accept(this))
        val rightValue = this.getValue(subAST.right.accept(this))

        return when {
            leftValue is Number && rightValue is Number -> NumberAST(rightValue - leftValue)

            else -> throw Exception("Cannot sum $rightValue and $leftValue")
        }
    }

    override fun visit(divAST: DivAST): AST {
        val leftValue = this.getValue(divAST.left.accept(this))
        val rightValue = this.getValue(divAST.right.accept(this))
        return when {
            leftValue is Number && rightValue is Number -> NumberAST(rightValue / leftValue)

            else -> throw Exception("Cannot sum $rightValue and $leftValue")
        }
    }

    override fun visit(mulAST: MulAST): AST {
        val leftValue = this.getValue(mulAST.left.accept(this))
        val rightValue = this.getValue(mulAST.right.accept(this))

        return when {
            leftValue is Number && rightValue is Number -> NumberAST(rightValue * leftValue)

            else -> throw Exception("Cannot sum $rightValue and $leftValue")
        }
    }

    override fun visit(stringAST: StringAST): AST = stringAST
    override fun visit(booleanAST: BooleanAST): AST = booleanAST

    /**
     * Visits a variable
     */
    override fun visit(variableAST: VariableAST): AST = variableAST
    override fun visit(blockAST: BlockAST): AST {
        setMemory(BlockMemory(mutableMapOf(), getMemory()))
        blockAST.statements.forEach { it.accept(this) }
        getMemory().getParent()?.let { setMemory(it) }

        return blockAST
    }

    override fun visit(ifAST: IfAST): AST {
        val condition = getType(ifAST.condition.accept(this))
        val value = getValue(ifAST.condition.accept(this))

        if (condition === Type.BOOLEAN) {
            if (value as Boolean) {
                ifAST.ifBlock.accept(this)
            } else {
                ifAST.elseBlock?.accept(this)
            }
        } else {
            throw Exception("Cannot evaluate $condition as a boolean")
        }

        return ifAST
    }

    override fun visit(inputAST: InputAST): AST {
        TODO("Not yet implemented")
    }

    override fun visit(numberAST: NumberAST): AST = numberAST
}

private operator fun Number.minus(number: Number): Number = this.toDouble() - number.toDouble()
private operator fun Number.div(number: Number): Number = this.toDouble() / number.toDouble()
private operator fun Number.plus(number: Number): Number = this.toDouble() + number.toDouble()
private operator fun Number.plus(string: String): String = "$this" + string
private operator fun Number.times(number: Number): Number = this.toDouble() * number.toDouble()

/**
 * Interpreter Interface
 */
sealed interface Interpreter : ASTVisitor {
    fun interpret(ast: AST)

    fun getMemory(): Memory
}
