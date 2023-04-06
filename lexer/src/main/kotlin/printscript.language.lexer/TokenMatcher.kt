package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

class TokenMatcher(private val tokenMap: Map<String, TokenType>){
    fun match(stringToken: String): Token {
        val tokenType = tokenMap[stringToken]
        if (tokenType != null) {
            return Token(tokenType)
        }
        return Token(TokenType.IDENTIFIER, stringToken)
    }
}