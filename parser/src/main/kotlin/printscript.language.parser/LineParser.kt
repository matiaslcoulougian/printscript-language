package printscript.language.parser

import ast.AST
import printscript.language.token.Token

interface LineParser {
    fun isValidDeclaration(tokens: List<Token>, parsers: List<LineParser> = emptyList()): Boolean
    fun parse(tokens: List<Token>, parsers: List<LineParser> = emptyList()): AST
}
