package printscript.language.parser

import ast.BlockAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class BlockParser : StatementParser {
    override fun isValidStatement(tokens: List<Token>): Boolean = tokens[0].type == TokenType.OPEN_BLOCK

    override fun parseStatement(tokens: List<Token>): BlockAST {
        checkExceptions(tokens)
        val completeParser = CompleteParser()
        val astList = completeParser.parse(tokens.subList(1, tokens.size - 1))
        return BlockAST(astList, tokens[0].line, tokens[0].column)
    }

    private fun checkExceptions(tokens: List<Token>) {
        if (tokens[tokens.size - 1].type != TokenType.CLOSE_BLOCK) throw Exception("Invalid block declaration for line ${tokens[0].line} column ${tokens[0].column}. Missing closing '}'")
    }
}
