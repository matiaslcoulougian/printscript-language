package printscript.language.lexer

import printscript.language.lexer.utils.avoidQuoteEncloseEndPattern
import printscript.language.lexer.utils.avoidQuoteEncloseStartPattern
import printscript.language.token.Token
import printscript.language.token.TokenType

class NumberLexer : Lexer {
    private val numberPattern = Regex("""$avoidQuoteEncloseStartPattern(\b(0|\d+(\.\d+)?)\b|\bnumber\b)$avoidQuoteEncloseEndPattern""")

    override fun getTokens(input: List<String>, line: Int): List<Token> {
        return input.flatMapIndexed { index, it ->
            numberPattern.findAll(it)
                .map { match ->
                    val value = match.value
                    val range = match.range
                    val currentLine = line + index + 1
                    if (value == "number") return@map Token(TokenType.NUMBER_TYPE, currentLine, range.first)
                    Token(TokenType.NUMBER_LITERAL, currentLine, range.first, value)
                }
                .toList()
        }
    }
}
