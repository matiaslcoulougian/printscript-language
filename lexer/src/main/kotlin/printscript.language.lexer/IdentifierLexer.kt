package printscript.language.lexer

import printscript.language.lexer.utils.avoidQuoteEncloseEndPattern
import printscript.language.lexer.utils.avoidQuoteEncloseStartPattern
import printscript.language.token.Token
import printscript.language.token.TokenType

class IdentifierLexer(private val reservedWords: List<String>) : Lexer {
    private val identifierPattern = Regex("$avoidQuoteEncloseStartPattern\\w*[a-zA-Z]\\w*$avoidQuoteEncloseEndPattern")

    override fun getTokens(input: String, line: Int): List<Token> {
        return identifierPattern.findAll(input)
            .filter { match ->
                match.value !in reservedWords
            }
            .map { match ->
                val value = match.value
                val range = match.range
                Token(TokenType.IDENTIFIER, line, range.first, value)
            }
            .toList()
    }
}
