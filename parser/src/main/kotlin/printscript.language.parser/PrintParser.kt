package printscript.language.parser

import ast.AST
import ast.PrintAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class PrintParser() : StatementParser {
    // Valid declarations:
    // -> Print(<...>) where argument is valid for any other parser
    override fun isValidStatement(tokens: List<Token>): Boolean {
        val statementParser = StatementParser()
        return tokens[0].type == TokenType.PRINTLN &&
            tokens[1].type == TokenType.OPEN_PARENTHESIS &&
            tokens[tokens.size - 1].type == TokenType.CLOSE_PARENTHESIS &&
            statementParser.isValid(tokens.subList(2, tokens.size - 1))
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val statementParser = StatementParser()
        val argument = statementParser.parseLine(tokens.subList(2, tokens.size - 1)) // removing PRINTLN and parenthesis
        return PrintAST(argument, tokens[0].line, tokens[0].column)
    }
}
