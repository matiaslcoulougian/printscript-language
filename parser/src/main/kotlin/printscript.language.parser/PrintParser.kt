package printscript.language.parser

import ast.AST
import ast.PrintAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class PrintParser() : LineParser {
    // Valid declarations:
    // -> Print(<...>) where argument is valid for any other parser
    override fun isValidDeclaration(tokens: List<Token>, parsers: List<LineParser>): Boolean {
        return tokens[0].type == TokenType.PRINTLN &&
            tokens[1].type == TokenType.OPEN_PARENTHESIS &&
            tokens[tokens.size - 1].type == TokenType.CLOSE_PARENTHESIS &&
            parsers.any { it.isValidDeclaration(tokens.subList(2, tokens.size - 1), parsers) }
    }

    override fun parse(tokens: List<Token>, parsers: List<LineParser>): AST {
        val validParser = parsers.find { it -> it.isValidDeclaration(tokens.subList(1, tokens.size - 1)) }
        if (validParser != null) {
            val argument = validParser.parse(tokens.subList(2, tokens.size - 1), parsers) // removing PRINTLN and parenthesis
            return PrintAST(argument)
        }
        throw Exception("Cant parse expression")
    }
}
