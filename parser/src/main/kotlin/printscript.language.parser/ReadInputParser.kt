package printscript.language.parser

import ast.AST
import ast.literalAST.NumberAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class ReadInputParser : StatementParser {
    override fun isValidStatement(tokens: List<Token>): Boolean {
        val shuntingYardParser = ShuntingYardParser()
        if (tokens[0].type != TokenType.READ_INPUT ||
            tokens[1].type != TokenType.OPEN_PARENTHESIS ||
            tokens[tokens.size - 1].type != TokenType.CLOSE_PARENTHESIS ||
            !shuntingYardParser.isValidStatement(tokens.subList(2, tokens.size-1))
        ) {
            return false
        }
        return true
    }

    override fun parseStatement(tokens: List<Token>): AST {
        // Todo: not yet implemented
        return NumberAST(5)
    }
}
