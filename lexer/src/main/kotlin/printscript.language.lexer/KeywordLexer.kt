package printscript.language.lexer

import printscript.language.lexer.utils.avoidQuoteEncloseEndPattern
import printscript.language.lexer.utils.avoidQuoteEncloseStartPattern
import printscript.language.token.Token
import printscript.language.token.TokenType

class KeywordLexer(private val keywordsMap: Map<String, TokenType>) : Lexer {
    val keywordPattern = Regex("""$avoidQuoteEncloseStartPattern(${keywordsMap.keys.joinToString("|")})$avoidQuoteEncloseEndPattern""")

    override fun getTokens(input: List<String>, line: Int): List<Token> {
        return input.flatMapIndexed { index, it ->
            keywordPattern.findAll(it)
                .map { match ->
                    val value = match.value
                    val range = match.range
                    Token(keywordsMap[value]!!, line + index + 1, range.first)
                }
                .toList()
        }
    }
}
