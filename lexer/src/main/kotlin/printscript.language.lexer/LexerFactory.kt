package printscript.language.lexer

import printscript.language.token.TokenType
import java.io.FileInputStream

class LexerFactory {
    private val initialKeywords = mapOf(
        "println" to TokenType.PRINTLN,
        "let" to TokenType.VARIABLE,
    )
    private val initialSymbols = mapOf(
        "+" to TokenType.SUM,
        "/" to TokenType.DIVISION,
        "-" to TokenType.SUBTRACTION,
        "*" to TokenType.PRODUCT,
        "=" to TokenType.EQUALS,
        "(" to TokenType.OPEN_PARENTHESIS,
        ")" to TokenType.CLOSE_PARENTHESIS,
        ";" to TokenType.EOL,
    )

    private val initialReservedWords = listOf(
        "let",
        "string",
        "number",
        "println",
    )
    fun createLexer(version: String, fileInputStream: FileInputStream): Lexer {
        return when (version) {
            "1.0" -> ComposedLexer(
                listOf(
                    IdentifierLexer(initialReservedWords),
                    StringLexer(),
                    NumberLexer(),
                    KeywordLexer(initialKeywords),
                    SymbolLexer(initialSymbols),
                ),
                StatementIterator(fileInputStream, false),
            )
            "1.1" -> ComposedLexer(
                listOf(
                    IdentifierLexer(initialReservedWords + listOf("const", "boolean", "true", "false", "readInput", "if", "else")),
                    StringLexer(),
                    NumberLexer(),
                    BooleanLexer(),
                    KeywordLexer(initialKeywords + mapOf("const" to TokenType.CONSTANT, "if" to TokenType.IF, "else" to TokenType.ELSE, "readInput" to TokenType.READ_INPUT)),
                    SymbolLexer(initialSymbols + mapOf("{" to TokenType.OPEN_BLOCK, "}" to TokenType.CLOSE_BLOCK)),
                ),
                StatementIterator(fileInputStream, true),
            )
            else -> throw Exception("Version $version not supported")
        }
    }
}
