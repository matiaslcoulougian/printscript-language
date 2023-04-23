package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

class SymbolLexer(private val symbolMap: Map<String, TokenType>) : Lexer {
    private var stringDelimiter = ""

    override fun getTokens(input: String, line: Int): List<Token> {
        val tokens = mutableListOf<Token>()
        for (index in input.indices) {
            val char = input[index]
            if ((char == '"' || char == '\'') && stringDelimiter == "") {
                stringDelimiter = "$char"
            } else if ((char == '"' || char == '\'') && stringDelimiter == "$char") {
                stringDelimiter = ""
            } else if (stringDelimiter == "") {
                val symbolTokenType = symbolMap["$char"]
                if (symbolTokenType != null) tokens.add(Token(symbolTokenType, line, index))
            }
        }
        return tokens
    }
}
