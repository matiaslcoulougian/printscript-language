package printscript.language.lexer

import printscript.language.token.Token

class ComposedLexer(private val lexers: List<Lexer>, private val statementIterator: StatementIterator) : Lexer {
    private var line = 0
    override fun getTokens(input: String, line: Int): List<Token> {
        val tokens = lexers.flatMap { it.getTokens(input, line) }
        return tokens.sortedBy { it.column }
    }
    override fun getTokens(): List<Token> {
        if (statementIterator.hasNext()) {
            val statement = statementIterator.next()!!
            line++
            return getTokens(statement, line)
        }
        return emptyList()
    }
}
