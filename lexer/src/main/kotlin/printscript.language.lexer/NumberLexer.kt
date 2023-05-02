package printscript.language.lexer

import printscript.language.lexer.utils.avoidQuoteEncloseEndPattern
import printscript.language.lexer.utils.avoidQuoteEncloseStartPattern
import printscript.language.token.Token
import printscript.language.token.TokenType

class NumberLexer : Lexer {
    private val numberPattern = Regex("""$avoidQuoteEncloseStartPattern(\b(0|\d+(\.\d+)?)\b|\bnumber\b)$avoidQuoteEncloseEndPattern""")

    override fun getTokens(input: String, line: Int): List<Token> {
        return numberPattern.findAll(input)
            .map { match ->
                val value = match.value
                val range = match.range
                if (value == "number") return@map Token(TokenType.NUMBER_TYPE, line, range.first)
                Token(TokenType.NUMBER_LITERAL, line, range.first, value)
            }
            .toList()
    }
}
