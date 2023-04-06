package printscript.language.token

enum class TokenType {
    IDENTIFIER,
    VARIABLE,
    CONSTANT,
    STRING_LITERAL,
    NUMBER_LITERAL,
    TRUE,
    FALSE,
    NUMBER_TYPE,
    STRING_TYPE,
    BOOLEAN_TYPE,
    PRINTLN,
    SUM,
    DIVISION,
    SUBTRACTION,
    PRODUCT,
    EQUALS,
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,
    EOL, // end of line
    EOF, // end of file
    IF,
    ELSE,
    OPEN_BLOCK,
    CLOSE_BLOCK,
    READ_INPUT
}
data class Token(val type: TokenType, val value: String = "")
