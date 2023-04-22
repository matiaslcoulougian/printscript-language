package printscript.language.parser

import ast.AST
import printscript.language.token.Token

interface StatementParser {
    fun isValidStatement(tokens: List<Token>): Boolean
    fun parseStatement(tokens: List<Token>): AST
}
