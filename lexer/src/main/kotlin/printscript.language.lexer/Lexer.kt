package printscript.language.lexer

import printscript.language.token.Token

interface Lexer {
    fun getTokens(input: List<String>, line: Int): List<Token>
    fun getTokens(): List<Token> = emptyList()
}
