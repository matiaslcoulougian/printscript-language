package printscript.language.parser

import ast.* // ktlint-disable no-wildcard-imports
import ast.NumberAST
import ast.StringAST
import printscript.language.token.Token
import printscript.language.token.TokenType
import java.util.Stack
import kotlin.Exception

class ShuntingYardParser : StatementParser {

    //    Valid declarations
    //    -> number literals, string literals, variables
    //    -> sum, difference, mul, div operators
    //    -> number literals, string literals, variables separated by operators
    //    -> parenthesis. Have to be opened and closed after
    //
    override fun isValidStatement(tokens: List<Token>): Boolean {
        try {
            checkExceptions(tokens)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val postfixExpression = infixToPostfix(tokens)

        val stack = Stack<AST>()
        try {
            for (token in postfixExpression) {
                when (token.type) {
                    TokenType.NUMBER_LITERAL -> stack.push(NumberAST(token.value.toDoubleOrNull() as Number, token.line, token.column))
                    TokenType.STRING_LITERAL -> stack.push(StringAST(token.value, token.line, token.column))
                    TokenType.IDENTIFIER -> stack.push(VariableAST(token.value, token.line, token.column))
                    TokenType.SUM -> stack.push(SumAST(stack.pop(), stack.pop(), token.line, token.column))
                    TokenType.SUBTRACTION -> stack.push(SubAST(stack.pop(), stack.pop(), token.line, token.column))
                    TokenType.PRODUCT -> stack.push(MulAST(stack.pop(), stack.pop(), token.line, token.column))
                    TokenType.DIVISION -> stack.push(DivAST(stack.pop(), stack.pop(), token.line, token.column))
                    else -> throw Exception("Invalid token for value expresion")
                }
            }
        } catch (e: Exception) {
            throw Exception("Invalid math expresion error")
        }
        return stack.pop()
    }
    private fun checkExceptions(tokens: List<Token>) {
        val parenthesisStack = Stack<Token>()
        val lastToken = Stack<Token>()
        for (token in tokens) {
            when (token.type) {
                TokenType.NUMBER_LITERAL,
                TokenType.STRING_LITERAL,
                TokenType.IDENTIFIER,
                -> {
                    if (lastToken.size > 0 && isOperand(lastToken.pop())) {
                        throw Exception("Missing operator")
                    }
                    lastToken.push(token)
                }
                TokenType.SUM,
                TokenType.SUBTRACTION,
                TokenType.PRODUCT,
                TokenType.DIVISION,
                -> {
                    if (lastToken.size == 0 || isOperator(lastToken.pop())) {
                        throw Exception("Cant concatenate operators")
                    }
                    lastToken.push(token)
                }
                TokenType.OPEN_PARENTHESIS -> parenthesisStack.push(token)
                TokenType.CLOSE_PARENTHESIS -> parenthesisStack.pop()
                else -> throw Exception("Invalid Token for shunting yard parser")
            }
        }
        if (isOperator(lastToken.pop())) throw Exception("Missing second operand")
    }

    private fun isOperand(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBER_LITERAL,
            TokenType.STRING_LITERAL,
            TokenType.IDENTIFIER,
            -> true
            else -> { false }
        }
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.SUM,
            TokenType.SUBTRACTION,
            TokenType.PRODUCT,
            TokenType.DIVISION,
            -> true
            else -> { false }
        }
    }
    private fun getOperatorPrecedence(operatorToken: Token): Int {
        return when (operatorToken.type) {
            TokenType.SUM -> 1
            TokenType.SUBTRACTION -> 1
            TokenType.PRODUCT -> 2
            TokenType.DIVISION -> 2
            else -> { throw Exception("Token is not an operator")
            }
        }
    }

    private fun infixToPostfix(input: List<Token>): List<Token> {
        val output = mutableListOf<Token>()
        val operatorStack = mutableListOf<Token>()
        for (token in input) {
            when (token.type) {
                TokenType.NUMBER_LITERAL,
                TokenType.STRING_LITERAL,
                TokenType.IDENTIFIER,
                -> output.add(token)

                TokenType.SUM,
                TokenType.SUBTRACTION,
                TokenType.DIVISION,
                TokenType.PRODUCT,
                -> {
                    while (operatorStack.isNotEmpty() &&
                        operatorStack.last().type != TokenType.OPEN_PARENTHESIS &&
                        getOperatorPrecedence(operatorStack.last()) >= getOperatorPrecedence(token)
                    ) {
                        output.add(operatorStack.removeLast())
                    }
                    operatorStack.add(token)
                }
                TokenType.OPEN_PARENTHESIS -> operatorStack.add(token)
                TokenType.CLOSE_PARENTHESIS -> {
                    while (operatorStack.isNotEmpty() && operatorStack.last().type != TokenType.OPEN_PARENTHESIS) {
                        output.add(operatorStack.removeLast())
                    }
                    if (operatorStack.isNotEmpty() && operatorStack.last().type == TokenType.OPEN_PARENTHESIS) {
                        operatorStack.removeLast()
                    }
                }
                else -> throw Exception("Invalid postifx Token")
            }
        }
        while (operatorStack.isNotEmpty()) {
            output.add(operatorStack.removeLast())
        }
        return output
    }
}
