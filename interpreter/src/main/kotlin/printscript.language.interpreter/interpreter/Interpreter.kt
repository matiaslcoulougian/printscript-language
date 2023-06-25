package printscript.language.interpreter.interpreter

import Type
import ast.* // ktlint-disable no-wildcard-imports
import ast.BooleanAST
import ast.LiteralAST
import ast.NumberAST
import ast.StringAST
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

    /**
     * Interprets the AST.
     */
    override fun interpret(ast: AST) {
        ast.accept(this)
    }

    override fun getMemory(): Memory = contextProvider.getMemory()

    private fun setMemory(memory: Memory) = contextProvider.setMemory(memory)

    private fun getType(ast: AST): Type {
        if (ast is VariableAST) {
            return getMemory().getType(ast.name)
        } else if (ast is LiteralAST<*>) {
            return ast.type
        } else if (ast is DeclarationAST) return ast.type
        return Type.UNDEFINED
    }

    private fun isConst(variable: VariableAST): Boolean = getMemory().getIsConst(variable.name)

    private fun getValue(ast: AST): Any? {
        if (ast is LiteralAST<*>) {
            return ast.value
        } else if (ast is VariableAST) return getMemory().get(ast.name).value
        return null
    }

    private fun getStringValue(ast: AST): String? {
        if (ast is LiteralAST<*>) {
            return ast.getStringValue()
        } else if (ast is VariableAST) {
            val memoryValue = getMemory().get(ast.name).value
            if (memoryValue is Double) return doubleToString(memoryValue)
            return memoryValue.toString()
        }
        return null
    }

    override fun visit(declarationAST: DeclarationAST): AST {
        addVariable(declarationAST)
        return declarationAST
    }

    override fun visit(assignationAST: AssignationAST): AST {
        val declaration = assignationAST.declaration
        var expression = assignationAST.expression.accept(this)
        when (expression) {
            is LiteralAST<*> -> {
                if (expression is LiteralInputAST) {
                    expression = inferenceType(expression, getType(declaration))
                }
                when (declaration) {
                    is DeclarationAST -> addVariable(declaration, expression)
                    is VariableAST -> setVariable(declaration, expression)

                    else -> throw Exception("Cannot assign $expression to $declaration")
                }
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

    private fun inferenceType(expression: LiteralInputAST, type: Type): LiteralAST<*> {
        return when (type) {
            Type.NUMBER -> NumberAST(expression.value.toDouble())
            Type.STRING -> StringAST(expression.value)
            Type.BOOLEAN -> BooleanAST(expression.value.toBooleanStrict())
            else -> throw Exception("Cannot inference type")
        }
    }

    private fun addVariable(declaration: DeclarationAST, expression: LiteralAST<*>? = null) {
        val validType = expression == null || validateTypes(declaration.type, expression)
        if (!validType) throw Exception("Cannot assign ${expression?.type.toString().lowercase()} value to ${declaration.type.toString().lowercase()} variable.")

        val invalidConst = declaration.isConst && expression == null
        if (invalidConst) throw Exception("Cannot declare constant without value.")

        val value = if (expression == null) null else this.getValue(expression)
        val variable = Variable(value, declaration.isConst, declaration.type)
        setMemory(getMemory().set(declaration.name, variable))
    }

    private fun validateTypes(type: Type, expression: AST): Boolean = type == getType(expression)

    override fun visit(printAST: PrintAST): AST {
        val toPrint = this.getStringValue(printAST.value.accept(this))
        if (toPrint != null) contextProvider.emit(toPrint)
        return printAST
    }

    override fun visit(sumAST: SumAST): AST {
        val leftValue = this.getValue(sumAST.left.accept(this))
        val rightValue = this.getValue(sumAST.right.accept(this))

        return when {
            leftValue is Double && rightValue is Double -> {
                NumberAST(leftValue + rightValue)
            }

            leftValue is String && rightValue is String -> {
                StringAST(leftValue + rightValue)
            }

            leftValue is String && rightValue is Double -> {
                StringAST(leftValue + doubleToString(rightValue))
            }

            leftValue is Double && rightValue is String -> {
                StringAST(doubleToString(leftValue) + rightValue)
            }

            else -> {
                throw Exception("Cannot sum $leftValue and $rightValue")
            }
        }
    }

    override fun visit(subAST: SubAST): AST {
        val leftValue = this.getValue(subAST.left.accept(this))
        val rightValue = this.getValue(subAST.right.accept(this))

        return when {
            leftValue is Double && rightValue is Double -> {
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
            leftValue is Double && rightValue is Double -> {
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
            leftValue is Double && rightValue is Double -> {
                NumberAST(leftValue * rightValue)
            }

            else -> {
                throw Exception("Cannot sum $leftValue and $rightValue")
            }
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
        getMemory().getParent()?.run { setMemory(this) }

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
        contextProvider.emit(getValue(inputAST.inputMsg.accept(this)).toString())
        return LiteralInputAST(contextProvider.read("1"), inputAST.line, inputAST.column)
    }

    override fun visit(literalInputAST: LiteralInputAST): AST = literalInputAST

    override fun visit(numberAST: NumberAST): AST = numberAST

    private fun doubleToString(value: Double) = if (value % 1 == 0.0) "${value.toInt()}" else "$value"
}

/**
 * Interpreter Interface
 */
sealed interface Interpreter : ASTVisitor {
    fun interpret(ast: AST)

    fun getMemory(): Memory
}
