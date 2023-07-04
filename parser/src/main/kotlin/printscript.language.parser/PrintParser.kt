package printscript.language.parser

import ast.AST
import ast.PrintAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class PrintParser() : StatementParser {
    // Valid declarations:
    // -> Print(<...>) where argument is valid for any other parser // PRINTLN, OPEN, STRING, CLOSE, EOL
    override fun isValidStatement(tokens: List<Token>): Boolean = tokens[0].type == TokenType.PRINTLN

    override fun parseStatement(tokens: List<Token>): AST {
        checkExceptions(tokens)
        val statementParser = StatementParser()
        val argument = statementParser.parseLine(tokens.subList(2, tokens.size - 2)) // removing PRINTLN, parenthesis and semicolon
        return PrintAST(argument, tokens[0].line, tokens[0].column)
    }

    private fun checkExceptions(tokens: List<Token>) {
        if (tokens[1].type != TokenType.OPEN_PARENTHESIS) throw Exception("Invalid print statement in line ${tokens[0].line} column ${tokens[0].column}. Missing opening '('")
        if (tokens[tokens.size - 1].type != TokenType.EOL) throw Exception("Invalid print statement in line ${tokens[0].line} column ${tokens[0].column}. Missing semicolon")
        if (tokens[tokens.size - 2].type != TokenType.CLOSE_PARENTHESIS) throw Exception("Invalid print statement in line ${tokens[0].line} column ${tokens[0].column}. Missing closing ')'")
        val statementParser = StatementParser()
        val isStatementValid = statementParser.isValid(tokens.subList(2, tokens.size - 2))
        if (!isStatementValid) throw Exception("Invalid print statement in line ${tokens[0].line} column ${tokens[0].column}. Invalid argument")
    }
}
