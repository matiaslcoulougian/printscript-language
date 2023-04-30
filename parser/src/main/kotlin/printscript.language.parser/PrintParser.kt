package printscript.language.parser

import ast.AST
import ast.PrintAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class PrintParser() : StatementParser {
    // Valid declarations:
    // -> Print(<...>) where argument is valid for any other parser
    override fun isValidStatement(tokens: List<Token>): Boolean {
        val shuntingYardParser = ShuntingYardParser()
        return tokens[0].type == TokenType.PRINTLN &&
            tokens[1].type == TokenType.OPEN_PARENTHESIS &&
            tokens[tokens.size - 2].type == TokenType.CLOSE_PARENTHESIS &&
            shuntingYardParser.isValidStatement(tokens.subList(2, tokens.size - 2))
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val statementParser = StatementParser()
        val argument = statementParser.parseLine(tokens.subList(2, tokens.size - 2)) // removing PRINTLN and parenthesis
        return PrintAST(argument, tokens[0].line, tokens[0].column)
    }
}
