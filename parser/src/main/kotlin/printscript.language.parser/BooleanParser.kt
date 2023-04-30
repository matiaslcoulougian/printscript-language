package printscript.language.parser

import ast.AST
import ast.BooleanAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class BooleanParser : StatementParser {
    override fun isValidStatement(tokens: List<Token>): Boolean =
        tokens.size == 1 && (tokens[0].type == TokenType.TRUE || tokens[0].type == TokenType.FALSE)

    override fun parseStatement(tokens: List<Token>): AST {
        return when (tokens[0].type) {
            TokenType.TRUE -> BooleanAST(true, tokens[0].line, tokens[0].column)
            TokenType.FALSE -> BooleanAST(false, tokens[0].line, tokens[0].column)
            else -> throw Exception("Could not parse boolean")
        }
    }
}
