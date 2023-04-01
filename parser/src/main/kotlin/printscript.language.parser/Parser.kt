package printscript.language.parser

import ast.*
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.Exception

class Parser(val lineParsers: List<LineParser>) {
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
        val linesList = ArrayList<ArrayList<Token>>()
        val remainingTokens = tokens.toMutableList()
        while (remainingTokens[0] != Token(TokenType.EOF)) {
            val indexEOL = remainingTokens.indexOf(Token(TokenType.EOL))
            val lineTokens = remainingTokens.subList(0, indexEOL)
            remainingTokens.subList(indexEOL + 1, remainingTokens.size).clear()
            linesList.add(lineTokens as ArrayList<Token>)
        }
        return linesList
    }
}
