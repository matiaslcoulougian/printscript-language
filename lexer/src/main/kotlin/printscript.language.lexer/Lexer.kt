package lexer

enum class TokenType {
    IDENTIFIER,
    DESIGNATOR,
    STRING_LITERAL,
    NUMBER_LITERAL,
    NUMBER_TYPE,
    STRING_TYPE,
    PRINTLN,
    OPERATION,
    EOL, // end of line
    EOF // end of file
}

class Lexer {
    private var currentPos = 0
    private fun getNextToken(input: String): Token {
        // Skip whitespace characters
        while (currentPos < input.length && (input[currentPos].isWhitespace() || input[currentPos] == '(' || input[currentPos] == ')')) {
            currentPos++
        }

        // If we have reached the end of file, return EOF token
        if (currentPos >= input.length) {
            return Token(TokenType.EOF.name, "")
        }

        // If we have reached the end of line, return EOL token
        if (input[currentPos] == ';') {
            currentPos++
            return Token(TokenType.EOL.name, "")
        }

        // Check for identifier or keyword token
        if (input[currentPos].isLetter()) {
            var value = ""
            while (currentPos < input.length && (input[currentPos].isLetterOrDigit() || input[currentPos] == '_')) {
                value += input[currentPos]
                currentPos++
            }
            return when (value) {
                "let" -> Token(TokenType.DESIGNATOR.name, value)
                "string" -> Token(TokenType.STRING_TYPE.name, value)
                "number" -> Token(TokenType.NUMBER_TYPE.name, value)
                "println" -> Token(TokenType.PRINTLN.name, value)
                else -> Token(TokenType.IDENTIFIER.name, value)
            }
        }

        // Check for string literal token with "
        if (input[currentPos] == '"' || input[currentPos] == '\'') {
            val quote = input[currentPos]
            var value = ""
            currentPos++
            while (currentPos < input.length && input[currentPos] != quote) {
                value += input[currentPos]
                currentPos++
            }
            currentPos++
            return Token(TokenType.STRING_LITERAL.name, value)
        }

        // Check for number literal token
        if (input[currentPos].isDigit()) {
            var value = ""
            while (currentPos < input.length && input[currentPos].isDigit()) {
                value += input[currentPos]
                currentPos++
            }
            return Token(TokenType.NUMBER_LITERAL.name, value)
        }

        // Check for operation token
        if (input[currentPos] == '+' || input[currentPos] == '-' || input[currentPos] == '*' || input[currentPos] == '/' || input[currentPos] == '=' || input[currentPos] == ':') {
            val value = input[currentPos].toString()
            currentPos++
            return Token(TokenType.OPERATION.name, value)
        }

        // If we haven't matched any token, raise an exception
        throw IllegalArgumentException("Invalid character: ${input[currentPos]}")
    }

    fun getAllTokens(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        while (true) {
            val token = getNextToken(input)
            tokens.add(token)
            if (token.type == TokenType.EOF.name) {
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
