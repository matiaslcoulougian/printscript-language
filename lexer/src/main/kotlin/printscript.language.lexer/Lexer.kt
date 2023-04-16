package printscript.language.lexer

import printscript.language.token.Token
import printscript.language.token.TokenType

private val tokenMap = mapOf(
    "+" to TokenType.SUM,
    "-" to TokenType.SUBTRACTION,
    "*" to TokenType.PRODUCT,
    "/" to TokenType.DIVISION,
    "=" to TokenType.EQUALS,
    "(" to TokenType.OPEN_PARENTHESIS,
    ")" to TokenType.CLOSE_PARENTHESIS,
    ";" to TokenType.EOL,
    "let" to TokenType.VARIABLE,
    "string" to TokenType.STRING_TYPE,
    "number" to TokenType.NUMBER_TYPE,
    "boolean" to TokenType.BOOLEAN_TYPE,
    "true" to TokenType.TRUE,
    "false" to TokenType.FALSE,
    "println" to TokenType.PRINTLN,
    "const" to TokenType.CONSTANT,
    "if" to TokenType.IF,
    "else" to TokenType.ELSE,
    "{" to TokenType.OPEN_BLOCK,
    "}" to TokenType.CLOSE_BLOCK,
    "readInput" to TokenType.READ_INPUT,
)

class Lexer {
    private var currentPos = 0
    private var tokenMatcher = TokenMatcher(tokenMap)

    private fun getNextToken(input: String): Token {
        // If we have reached the end of file, return EOF token
        if (currentPos >= input.length) return Token(TokenType.EOF)

        // Skip whitespace characters
        while (checkIgnoreChars(input)) {
            currentPos++
        }

        // Check for string literal token with " or '
        if (input[currentPos] == '"' || input[currentPos] == '\'') return checkStringLiteral(input)

        // Check for number literal token
        if (input[currentPos].isDigit()) return checkNumberLiteral(input)

        // Check for identifier or keyword token
        if (input[currentPos].isLetter()) return checkIdentifierOrKeyword(input)

        // Check for one character tokens
        val token = input[currentPos]
        currentPos++
        return tokenMatcher.match("$token")
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
        var tokenValue = ""
        while (currentPos < input.length && (input[currentPos].isLetterOrDigit() || input[currentPos] == '_')) {
            tokenValue += input[currentPos]
            currentPos++
        }
        return tokenMatcher.match(tokenValue)
    }

    private fun checkIgnoreChars(input: String) = currentPos < input.length && (input[currentPos].isWhitespace() || input[currentPos] == ':')

    fun getTokens(input: String): List<Token> {
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
