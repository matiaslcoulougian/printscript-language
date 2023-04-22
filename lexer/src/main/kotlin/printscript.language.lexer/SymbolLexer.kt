package printscript.language.lexer

import printscript.language.lexer.utils.avoidQuoteEncloseEndPattern
import printscript.language.lexer.utils.avoidQuoteEncloseStartPattern
import printscript.language.token.Token
import printscript.language.token.TokenType

class SymbolLexer(private val symbolMap: Map<String, TokenType>) : Lexer {
    val escapedSymbolList = symbolMap.keys.map { symbol -> Regex("[\\^+*?{}\\[\\]()$.|]").replace(symbol, "\\\\$0") }
    val symbolPattern = Regex("""$avoidQuoteEncloseStartPattern(${escapedSymbolList.joinToString("|")})$avoidQuoteEncloseEndPattern""")
    override fun getTokens(input: String, line: Int): List<Token> {
        return symbolPattern.findAll(input)
            .map { match ->
                val value = match.value
                val range = match.range
                Token(symbolMap[value]!!, line, range.first)
            }
            .toList()
    }
}
