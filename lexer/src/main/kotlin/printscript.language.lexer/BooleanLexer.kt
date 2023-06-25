package printscript.language.lexer

import printscript.language.lexer.utils.avoidQuoteEncloseEndPattern
import printscript.language.lexer.utils.avoidQuoteEncloseStartPattern
import printscript.language.token.Token
import printscript.language.token.TokenType

class BooleanLexer : Lexer {
    private val booleanPattern = Regex("$avoidQuoteEncloseStartPattern(\\btrue\\b|\\bfalse\\b|\\bboolean\\b)$avoidQuoteEncloseEndPattern")
    override fun getTokens(input: List<String>, line: Int): List<Token> {
        return input.flatMapIndexed { index, it ->
            booleanPattern.findAll(it).map { match ->
                val value = match.value
                val range = match.range
                val currentLine = line + index + 1
                when (value) {
                    "true" -> Token(TokenType.TRUE, currentLine, range.first)
                    "false" -> Token(TokenType.FALSE, currentLine, range.first)
                    "boolean" -> Token(TokenType.BOOLEAN_TYPE, currentLine, range.first)
                    else -> throw Exception("Unknown boolean value: $value")
                }
            }.toList()
        }
    }
}
