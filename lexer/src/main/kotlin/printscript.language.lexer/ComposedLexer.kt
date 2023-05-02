package printscript.language.lexer

import printscript.language.token.Token

/* Composed Lexer class returns list of tokens using multiple lexers to match the tokens
    Receives a string iterator that outputs statement lines. Statement are defined by as
    a string that ends with a semicolon (;) or a block of code surrounded by curly braces ({})
*/

class ComposedLexer(private val lexers: List<Lexer>, private val statementIterator: Iterator<String?>) : Lexer {
    private var line = 0

    // Get tokens from all lexers and sort them by column
    override fun getTokens(input: String, line: Int): List<Token> {
        val tokens = lexers.flatMap { it.getTokens(input, line) }
        return tokens.sortedBy { it.column }
    }

    // Get tokens from the next statement using the statement iterator
    override fun getTokens(): List<Token> {
        if (statementIterator.hasNext()) {
            val statement = statementIterator.next()!!
            line++
            return getTokens(statement, line)
        }
        return emptyList()
    }
}
