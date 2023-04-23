package printscript.language.parser

import ast.AST
import printscript.language.token.Token

class ASTIterator(
    private val parser: Parser,
    private val tokenListIterator: Iterator<List<Token>>
) : Iterator<AST?> {
    private var nextAST: AST? = null;
    override fun hasNext(): Boolean {
        if (tokenListIterator.hasNext()){
            val tokens = tokenListIterator.next()
            nextAST = parser.parseLine(tokens)
        }
        return nextAST != null
    }

    override fun next(): AST? {
        if (nextAST == null) {
            if (!tokenListIterator.hasNext()) return null;
            val tokens = tokenListIterator.next()
            nextAST = parser.parseLine(tokens)
        }
        val newAST = nextAST;
        nextAST = null;
        return newAST;
    }

}