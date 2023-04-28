package printscript.language.lexer

import printscript.language.lexer.utils.avoidQuoteEncloseEndPattern
import printscript.language.lexer.utils.avoidQuoteEncloseStartPattern
import printscript.language.token.Token
import printscript.language.token.TokenType

class BooleanLexer : Lexer {
    private val booleanPattern = Regex("$avoidQuoteEncloseStartPattern(\\btrue\\b|\\bfalse\\b|\\bboolean\\b)$avoidQuoteEncloseEndPattern")
    override fun getTokens(input: String, line: Int): List<Token> {
        return booleanPattern.findAll(input)
            .map { match ->
                val value = match.value
                val range = match.range
                when (value) {
                    "true" -> Token(TokenType.TRUE, line, range.first)
                    "false" -> Token(TokenType.FALSE, line, range.first)
                    "boolean" -> Token(TokenType.BOOLEAN_TYPE, line, range.first)
                    else -> throw Exception("Unknown boolean value: $value")
                }
            }
            .toList()
    }
}
