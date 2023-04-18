package printscript.language.token

enum class TokenType {
    IDENTIFIER,
    VARIABLE,
    STRING_LITERAL,
    NUMBER_LITERAL,
    NUMBER_TYPE,
    STRING_TYPE,
    PRINTLN,
    SUM,
    DIVISION,
    SUBTRACTION,
    PRODUCT,
    EQUALS,
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,
    EOL, // end of line
}
data class Token(val type: TokenType, val value: String = "")
