package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

class StringLexer : Lexer {
    private val stringPattern = Regex("""(['"])(.*?)(?<!\\)\1|\bstring\b""")
    override fun getTokens(input: List<String>, line: Int): List<Token> {
        return input.flatMapIndexed { index, it ->
            stringPattern.findAll(it)
                .map { match ->
                    if (match.value == "string") {
                        return@map Token(TokenType.STRING_TYPE, line + index + 1, match.range.first)
                    }
                    val value = match.groupValues[2]
                    val range = match.range
                    Token(TokenType.STRING_LITERAL, line + index + 1, range.first, value)
                }
                .toList()
        }
    }
}
