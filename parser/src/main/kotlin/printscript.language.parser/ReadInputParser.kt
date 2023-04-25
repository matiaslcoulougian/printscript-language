package printscript.language.parser

import Type
import ast.AST
import ast.InputAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class ReadInputParser : StatementParser {
    // [readInput] [(] [StringLiteral] [)] [TYPE]
    override fun isValidStatement(tokens: List<Token>): Boolean {
        val shuntingYardParser = ShuntingYardParser()
        return (
            tokens[0].type == TokenType.READ_INPUT &&
                tokens[1].type == TokenType.OPEN_PARENTHESIS &&
                tokens[tokens.size - 2].type == TokenType.CLOSE_PARENTHESIS &&
                isType(tokens[tokens.size - 1]) &&
                shuntingYardParser.isValidStatement(tokens.subList(2, tokens.size - 2))
            )
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val argumentParser = StatementParser()
        val argument = argumentParser.parse(tokens.subList(2, tokens.size - 2)) // removing parentheses, println and type
        return InputAST(argument[0], getType(tokens[tokens.size - 1]), tokens[0].line, tokens[0].column)
    }

    private fun isType(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING_TYPE,
            TokenType.BOOLEAN_TYPE,
            TokenType.NUMBER_TYPE,
            -> true
            else -> false
        }
    }

    private fun getType(token: Token): Type {
        return when (token.type) {
            TokenType.STRING_TYPE -> Type.STRING
            TokenType.BOOLEAN_TYPE -> Type.BOOLEAN
            TokenType.NUMBER_TYPE -> Type.NUMBER
            else -> throw Exception("not a type")
        }
    }
}
