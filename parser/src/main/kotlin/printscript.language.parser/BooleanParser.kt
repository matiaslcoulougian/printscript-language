package printscript.language.parser

import ast.AST
import ast.literalAST.BooleanAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class BooleanParser : StatementParser {
    override fun isValidStatement(tokens: List<Token>): Boolean {
        return tokens.size == 1 && (tokens[0].type == TokenType.TRUE || tokens[0].type == TokenType.FALSE)
    }

    override fun parseStatement(tokens: List<Token>): AST {
        return when (tokens[0].type) {
            TokenType.TRUE -> BooleanAST(true)
            TokenType.FALSE -> BooleanAST(false)
            else -> throw Exception("Could not parse boolean")
        }
    }
}
