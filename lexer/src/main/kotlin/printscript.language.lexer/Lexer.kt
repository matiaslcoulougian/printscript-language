package printscript.language.lexer

import printscript.language.token.Token

interface Lexer {
    fun getTokens(input: String, line: Int): List<Token>
    fun getTokens(): List<Token> = emptyList()
}
