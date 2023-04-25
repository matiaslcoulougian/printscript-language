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
            IfParser(),
            BlockParser(),
            AssignationParser(),
            ReadInputParser(),
        ),
    )
}

fun StatementParser(): Parser {
    return Parser(
        listOf(
            ShuntingYardParser(),
            BooleanParser(),
            ReadInputParser(),
        ),
    )
}

class Parser(private val statementParsers: List<StatementParser>) {
    fun isValid(tokens: List<Token>): Boolean {
        val lines = getLines(tokens)
        for (line in lines) {
            val isLineValid = statementParsers.any { it.isValidStatement(line) }
            if (!isLineValid) return false
        }
        return true
    }

    fun parse(tokens: List<Token>): List<AST> {
        val lines = getLines(tokens)
        val astList = ArrayList<AST>()
        for (line in lines) {
            val validParser = statementParsers.find { it -> it.isValidStatement(line) }
            if (validParser != null) {
                astList.add(validParser.parseStatement(line))
            } else {
                throw Exception("Cant parse expression")
            }
        }
        return astList
    }
    fun parseLine(tokens: List<Token>): AST {
        return statementParsers.find { it.isValidStatement(tokens) }?.parseStatement(tokens)
            ?: throw Exception("Cant parse expression from line " + tokens[0].line + " to line " + tokens[tokens.size - 1].line + ".")
    }

    // Gets all tokens and separates them into lines, removing the semicolons and EOF token
    private fun getLines(tokens: List<Token>): List<List<Token>> {
        var linesList = emptyList<List<Token>>()
        var remainingTokens = tokens
        while (remainingTokens.isNotEmpty()) {
            val indexEOL = getNextEOLIndex(remainingTokens)
            if (indexEOL < 0) {
                val lineTokens = remainingTokens
                remainingTokens = emptyList()
                linesList = linesList.plus(listOf(lineTokens))
            } else {
                val lineTokens = remainingTokens.subList(0, indexEOL)
                remainingTokens = remainingTokens.slice(IntRange(indexEOL + 1, remainingTokens.size - 1))
                linesList = linesList.plus(listOf(lineTokens))
            }
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
                else -> {}
            }
        }
        return -1
    }
}
