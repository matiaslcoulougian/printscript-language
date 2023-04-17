package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

private val tokenMap = mapOf(
    "let" to TokenType.DESIGNATOR,
    "+" to TokenType.SUM,
    "-" to TokenType.SUBTRACTION,
    "*" to TokenType.PRODUCT,
    "/" to TokenType.DIVISION,
    "=" to TokenType.EQUALS,
    "(" to TokenType.OPEN_PARENTHESIS,
    ")" to TokenType.CLOSE_PARENTHESIS,
    ";" to TokenType.EOL,
    "string" to TokenType.STRING_TYPE,
    "number" to TokenType.NUMBER_TYPE,
    "println" to TokenType.PRINTLN,
)

class Lexer {
    private var endChars = listOf(' ', ';', ':', ')', '(', '+', '-', '*', '/', '=')
    private var ignoreChars = listOf(' ', ':')
    private var stringDelimiters = listOf('"', '\'')
    private val EMTPY_CHAR = '\u0000'
    fun getTokens(line: String): List<Token> {
        val words = this.getWordsFromLine(line)
        return words.map { this.matchWordToToken(it) }
    }
    private fun matchWordToToken(word: String): Token {
        val firstChar = word.first()
        if (stringDelimiters.contains(firstChar)) {
            val stringLiteral = word.replace("$firstChar", "")
            return Token(TokenType.STRING_LITERAL, stringLiteral)
        } else if (firstChar.isDigit()) {
            return Token(TokenType.NUMBER_LITERAL, word)
        }
        val tokenType = tokenMap[word]
        if (tokenType != null) return Token(tokenType)
        return Token(TokenType.IDENTIFIER, word)
    }
    private fun getWordsFromLine(line: String): List<String> {
        val words = mutableListOf<String>()
        var accumulated = ""
        var stringDelimiter = EMTPY_CHAR
        for (char in line) {
            if (isStringDelimiter(char)) {
                when (stringDelimiter) {
                    EMTPY_CHAR -> {
                        accumulated += char
                        stringDelimiter = char
                    }
                    char -> {
                        accumulated += char
                        words.add(accumulated)
                        accumulated = ""
                        stringDelimiter = EMTPY_CHAR
                    }
                    else -> accumulated += char
                }
            } else if (isEndOfWord(stringDelimiter, char, accumulated)) {
                words.add(accumulated)
                if (!isIgnoreChar(char)) words.add("$char")
                accumulated = ""
            } else {
                if (!isEmptyChar(stringDelimiter) || !isIgnoreChar(char)) accumulated += char
            }
        }
        // add last character accumulated
        if (accumulated.isNotEmpty()) words.add(accumulated)
        return words
    }
    private fun isIgnoreChar(char: Char) = ignoreChars.contains(char)

    private fun isEndOfWord(stringDelimiter: Char, char: Char, accumulated: String) =
        isEmptyChar(stringDelimiter) && endChars.contains(char) && accumulated.isNotEmpty()

    private fun isEmptyChar(stringDelimiter: Char) = stringDelimiter == EMTPY_CHAR

    private fun isStringDelimiter(char: Char) = stringDelimiters.contains(char)
}
