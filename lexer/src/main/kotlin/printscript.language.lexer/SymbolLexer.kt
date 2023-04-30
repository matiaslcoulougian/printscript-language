package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

class SymbolLexer(private val symbolMap: Map<String, TokenType>, private val stringDelimiters: List<Char> = listOf('\'', '"')) : Lexer {
    private var currentDelimiter = "" // The current string delimiter that we are in

    override fun getTokens(input: String, line: Int): List<Token> {
        val tokens = mutableListOf<Token>()
        for (index in input.indices) {
            val char = input[index]
            // If the current character is a delimiter, then we toggle the current delimiter
            if (stringDelimiters.contains(char)) {
                currentDelimiter = if (currentDelimiter == "$char") "" else "$char"
            }
            // If the current character is a symbol, then we add it to the list of tokens
            else if (currentDelimiter == "") {
                val symbolTokenType = symbolMap["$char"]
                if (symbolTokenType != null) tokens.add(Token(symbolTokenType, line, index))
            }
        }
        return tokens
    }
}
