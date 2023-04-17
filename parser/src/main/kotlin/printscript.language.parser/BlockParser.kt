package printscript.language.parser

import ast.AST
import ast.BlockAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class BlockParser : LineParser {
    override fun isValidDeclaration(tokens: List<Token>, parsers: List<LineParser>): Boolean {
        if (tokens[0].type != TokenType.OPEN_BLOCK || tokens[tokens.size - 1].type != TokenType.CLOSE_BLOCK) return false
        val lines = getLines(tokens.subList(1, tokens.size - 1))
        for (line in lines) {
            val isLineValid = parsers.any { it.isValidDeclaration(line, parsers) }
            if (!isLineValid) return false
        }
        return true
    }

    override fun parse(tokens: List<Token>, parsers: List<LineParser>): AST {
        val lines = getLines(tokens.subList(1, tokens.size - 1))
        val astList = ArrayList<AST>()
        for (line in lines) {
            val validParser = parsers.find { it -> it.isValidDeclaration(line, parsers) }
            if (validParser != null) {
                astList.add(validParser.parse(line, parsers))
            } else {
                throw Exception("Cant parse expression")
            }
        }
        return BlockAST(astList)
    }

    private fun getLines(tokens: List<Token>): List<List<Token>> {
        var linesList = emptyList<List<Token>>()
        var remainingTokens = tokens
        while (remainingTokens[0] != Token(TokenType.EOF)) {
            val indexEOL = getNextEOLIndex(remainingTokens)
            val lineTokens = remainingTokens.subList(0, indexEOL)
            remainingTokens = remainingTokens.slice(IntRange(indexEOL + 1, remainingTokens.size - 1))
            linesList = linesList.plus(listOf(lineTokens))
        }
        return linesList
    }

    private fun getNextEOLIndex(tokens: List<Token>): Int {
        // use this function to avoid separating lines when EOL is inside a block
        var depth = 0
        for ((index, token) in tokens.withIndex()) {
            when (token.type) {
                TokenType.OPEN_BLOCK -> depth++
                TokenType.CLOSE_BLOCK -> depth--
                TokenType.EOL -> if (depth == 0) return index
            }
        }
        return -1
    }
}
