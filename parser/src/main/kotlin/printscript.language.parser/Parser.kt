package printscript.language.parser

import ast.* // ktlint-disable no-wildcard-imports
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.Exception

fun CompleteParser(): Parser {
    return Parser(
        listOf(
            ShuntingYardParser(),
            DeclarationParser(),
            PrintParser(),
        ),
    )
}

class Parser(private val lineParsers: List<LineParser>) {
    fun isValid(tokens: List<Token>): Boolean {
        val lines = getLines(tokens)
        for (line in lines) {
            val isLineValid = lineParsers.any { it.isValidDeclaration(line, lineParsers) }
            if (!isLineValid) return false
        }
        return true
    }

    fun parse(tokens: List<Token>): List<AST> {
        val lines = getLines(tokens)
        val astList = ArrayList<AST>()
        for (line in lines) {
            val validParser = lineParsers.find { it -> it.isValidDeclaration(line, lineParsers) }
            if (validParser != null) {
                astList.add(validParser.parse(line, lineParsers))
            } else {
                throw Exception("Cant parse expression")
            }
        }
        return astList
    }
    fun parseLine(tokens: List<Token>): AST {
        val validParser = lineParsers.find { it -> it.isValidDeclaration(tokens, lineParsers) }
        if (validParser != null) {
            return validParser.parse(tokens, lineParsers)
        } else {
            throw Exception("Cant parse expression, no valid parser found")
        }
    }

    // Gets all tokens and separates them into lines, removing the semicolons and EOF token
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
