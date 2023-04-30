package printscript.language.parser

import ast.AST
import ast.InputAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class ReadInputParser : StatementParser {
    override fun isValidStatement(tokens: List<Token>): Boolean {
        val shuntingYardParser = ShuntingYardParser()
        return !(
            tokens[0].type != TokenType.READ_INPUT ||
                tokens[1].type != TokenType.OPEN_PARENTHESIS ||
                tokens[tokens.size - 2].type != TokenType.CLOSE_PARENTHESIS ||
                !shuntingYardParser.isValidStatement(tokens.subList(2, tokens.size-2))
            )
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val argumentParser = StatementParser()
        val argument = argumentParser.parse(tokens.subList(2, tokens.size - 2)) // removing parentheses and println
        return InputAST(argument[0], tokens[0].line, tokens[0].column)
    }
}
