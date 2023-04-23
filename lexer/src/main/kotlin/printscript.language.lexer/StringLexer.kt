package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

class StringLexer : Lexer {
    private val stringPattern = Regex("""(['"])(.*?)(?<!\\)\1|\bstring\b""")
    override fun getTokens(input: String, line: Int): List<Token> {
        return stringPattern.findAll(input)
            .map { match ->
                if (match.value == "string") {
                    return@map Token(TokenType.STRING_TYPE, line, match.range.first)
                }
                val value = match.groupValues[2]
                val range = match.range
                Token(TokenType.STRING_LITERAL, line, range.first, value)
            }
            .toList()
    }
}
