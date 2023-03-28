package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

class Lexer {
    private var currentPos = 0
    private fun getNextToken(input: String): Token {
        // Skip whitespace characters
        while (checkIgnoreChars(input)) {
            currentPos++
        }

        // If we have reached the end of file, return EOF token
        if (currentPos >= input.length) return Token(TokenType.EOF, "")

        // Check for identifier or keyword token
        if (input[currentPos].isLetter()) {
            return checkIdentifierOrKeyword(input)
        }

        // Check for string literal token with " or '
        if (input[currentPos] == '"' || input[currentPos] == '\'') {
            return checkStringLiteral(input)
        }

        // Check for number literal token
        if (input[currentPos].isDigit()) {
            return checkNumberLiteral(input)
        }

        val token = input[currentPos]
        currentPos++

        // Check for operation token or equals
        when (token) {
            '+' -> return Token(TokenType.SUM)
            '-' -> return Token(TokenType.SUBTRACTION)
            '*' -> return Token(TokenType.PRODUCT)
            '/' -> return Token(TokenType.DIVISION)
            '=' -> return Token(TokenType.EQUALS)
            '(' -> return Token(TokenType.OPEN_PARENTHESIS)
            ')' -> return Token(TokenType.CLOSE_PARENTHESIS)
            ';' -> return Token(TokenType.EOL)
        }

        // If we haven't matched any token, raise an exception
        throw IllegalArgumentException("Invalid character: ${input[currentPos]}")
    }

    private fun checkNumberLiteral(input: String): Token {
        var value = ""
        while (currentPos < input.length && input[currentPos].isDigit()) {
            value += input[currentPos]
            currentPos++
        }
        return Token(TokenType.NUMBER_LITERAL, value)
    }

    private fun checkStringLiteral(input: String): Token {
        val quote = input[currentPos]
        var value = ""
        currentPos++
        while (currentPos < input.length && input[currentPos] != quote) {
            value += input[currentPos]
            currentPos++
        }
        currentPos++
        return Token(TokenType.STRING_LITERAL, value)
    }

    private fun checkIdentifierOrKeyword(input: String): Token {
        var value = ""
        while (currentPos < input.length && (input[currentPos].isLetterOrDigit() || input[currentPos] == '_')) {
            value += input[currentPos]
            currentPos++
        }
        return when (value) {
            "let" -> Token(TokenType.DESIGNATOR)
            "string" -> Token(TokenType.STRING_TYPE)
            "number" -> Token(TokenType.NUMBER_TYPE)
            "println" -> Token(TokenType.PRINTLN)
            else -> Token(TokenType.IDENTIFIER, value)
        }
    }

    private fun checkIgnoreChars(input: String) =
        currentPos < input.length &&
            (
                input[currentPos].isWhitespace() ||
                    input[currentPos] == ':'
                )

    fun getAllTokens(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        while (true) {
            val token = getNextToken(input)
            tokens.add(token)
            if (token.type == TokenType.EOF) {
                break
            }
        }
        resetCurrentPos()
        return tokens
    }

    private fun resetCurrentPos() {
        currentPos = 0
    }
}
