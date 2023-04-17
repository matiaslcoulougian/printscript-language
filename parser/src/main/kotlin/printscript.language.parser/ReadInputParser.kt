package printscript.language.parser

import ast.AST
import ast.ReadInputAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class ReadInputParser : LineParser {
    override fun isValidDeclaration(tokens: List<Token>, parsers: List<LineParser>): Boolean {
        return tokens.size == 1 && tokens[0].type == TokenType.READ_INPUT
    }

    override fun parse(tokens: List<Token>, parsers: List<LineParser>): AST {
        return ReadInputAST()
    }
}
