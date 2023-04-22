package printscript.language.parser

import ast.BlockAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class BlockParser : StatementParser {
    override fun isValidStatement(tokens: List<Token>): Boolean {
        val completeParser = CompleteParser()
        if (tokens[0].type != TokenType.OPEN_BLOCK || tokens[tokens.size - 1].type != TokenType.CLOSE_BLOCK) return false
        return completeParser.isValid(tokens.subList(1, tokens.size - 1))
    }

    override fun parseStatement(tokens: List<Token>): BlockAST {
        val completeParser = CompleteParser()
        val astList = completeParser.parse(tokens.subList(1, tokens.size - 1))
        return BlockAST(astList)
    }
}
