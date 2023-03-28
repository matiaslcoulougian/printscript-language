package printscript.language.token

enum class TokenType {
    IDENTIFIER,
    DESIGNATOR,
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
    EOF // end of file
}
data class Token(val type: TokenType, val value: String = "")