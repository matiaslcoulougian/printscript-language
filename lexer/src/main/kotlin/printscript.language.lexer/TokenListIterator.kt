package printscript.language.lexer

import printscript.language.token.Token

class TokenListIterator(private val lexer: Lexer) : Iterator<List<Token>> {
    private var nextTokenList: List<Token> = emptyList()

    override fun hasNext(): Boolean {
        if (nextTokenList.isEmpty()) nextTokenList = lexer.getTokens()
        return nextTokenList.isNotEmpty()
    }

    override fun next(): List<Token> {
        if (nextTokenList.isEmpty()) nextTokenList = lexer.getTokens()
        val tokenList = nextTokenList
        nextTokenList = emptyList()
        return tokenList
    }
}
