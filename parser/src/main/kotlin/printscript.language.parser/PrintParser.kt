package printscript.language.parser

import ast.AST
import ast.PrintAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class PrintParser() : StatementParser {
    // Valid declarations:
    // -> Print(<...>) where argument is valid for any other parser // PRINTLN, OPEN, STRING, CLOSE, EOL
    override fun isValidStatement(tokens: List<Token>): Boolean {
        val statementParser = StatementParser()
        return tokens[0].type == TokenType.PRINTLN &&
            tokens[1].type == TokenType.OPEN_PARENTHESIS &&
            tokens[tokens.size - 2].type == TokenType.CLOSE_PARENTHESIS &&
            tokens[tokens.size - 1].type == TokenType.EOL &&
            statementParser.isValid(tokens.subList(2, tokens.size - 2))
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val statementParser = StatementParser()
        val argument = statementParser.parseLine(tokens.subList(2, tokens.size - 2)) // removing PRINTLN, parenthesis and semicolon
        return PrintAST(argument, tokens[0].line, tokens[0].column)
    }
}
