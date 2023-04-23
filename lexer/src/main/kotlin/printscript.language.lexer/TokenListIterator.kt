package printscript.language.lexer

import printscript.language.token.Token

class TokenListIterator(private val lexer: Lexer) : Iterator<List<Token>?> {
    private var nextTokenList: List<Token>? = null

    override fun hasNext(): Boolean {
        if (nextTokenList == null) nextTokenList = lexer.getTokens()
        return nextTokenList != null
    }

    override fun next(): List<Token>? {
        if (nextTokenList == null) nextTokenList = lexer.getTokens()
        val tokenList = nextTokenList
        nextTokenList = null
        return tokenList
    }
}
