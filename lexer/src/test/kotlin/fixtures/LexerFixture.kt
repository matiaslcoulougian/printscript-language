package fixtures

import printscript.language.token.Token
import printscript.language.token.TokenType

fun getLexStringVariableDeclarationResponse(identifier: String, literal: String): List<Token> = listOf(
    Token(TokenType.DESIGNATOR),
    Token(
        TokenType.IDENTIFIER,
        identifier,
    ),
    Token(TokenType.STRING_TYPE),
    Token(
        TokenType.EQUALS,
    ),
    Token(TokenType.STRING_LITERAL, literal),
    Token(TokenType.EOL),
)

fun getLexStringVariableWithDoubleQuoteResponse(identifier: String, literal: String): List<Token> = listOf(
    Token(
        TokenType.DESIGNATOR,
    ),
    Token(TokenType.IDENTIFIER, identifier),
    Token(TokenType.STRING_TYPE),
    Token(TokenType.EQUALS),
    Token(
        TokenType.STRING_LITERAL,
        literal,
    ),
    Token(TokenType.EOL),
)

fun getLexNumberVariableDeclarationResponse(identifier: String, literal: String): List<Token> = listOf(
    Token(TokenType.DESIGNATOR),
    Token(
        TokenType.IDENTIFIER,
        identifier,
    ),
    Token(TokenType.NUMBER_TYPE),
    Token(TokenType.EQUALS),
    Token(TokenType.NUMBER_LITERAL, literal),
    Token(
        TokenType.EOL,
    ),
)

fun getLexPrintLnFunctionResponse(literal: String): List<Token> = listOf(
    Token(TokenType.PRINTLN),
    Token(TokenType.OPEN_PARENTHESIS),
    Token(
        TokenType.STRING_LITERAL,
        literal,
    ),
    Token(TokenType.CLOSE_PARENTHESIS),
    Token(TokenType.EOL),
)

fun getLexPrintLnFunctionWithIdentifierResponse(identifier: String): List<Token> = listOf(
    Token(TokenType.PRINTLN),
    Token(
        TokenType.OPEN_PARENTHESIS,
    ),
    Token(TokenType.IDENTIFIER, identifier),
    Token(TokenType.CLOSE_PARENTHESIS),
    Token(
        TokenType.EOL,
    ),
)

fun getLexStringConcatenationResponse(firstLiteral: String, secondLiteral: String): List<Token> = listOf(
    Token(TokenType.STRING_LITERAL, firstLiteral),
    Token(
        TokenType.SUM,
    ),
    Token(TokenType.STRING_LITERAL, secondLiteral),
    Token(TokenType.EOL),
)

fun getLexStringConcatenationWithIdentifierResponse(identifier: String, literal: String): List<Token> = listOf(
    Token(
        TokenType.STRING_LITERAL,
        literal,
    ),
    Token(TokenType.SUM),
    Token(TokenType.IDENTIFIER, identifier),
    Token(
        TokenType.EOL,
    ),
)

fun geLexOperationResponse(firstLiteral: String, secondLiteral: String, operationType: TokenType): List<Token> = listOf(
    Token(TokenType.NUMBER_LITERAL, firstLiteral),
    Token(operationType),
    Token(TokenType.NUMBER_LITERAL, secondLiteral),
    Token(
        TokenType.EOL,
    ),
)

fun geLexOperationsWithParenthesisResponse(firstLiteral: String, secondLiteral: String, thirdLiteral: String, firstOperation: TokenType, secondOperation: TokenType): List<Token> = listOf(
    Token(TokenType.OPEN_PARENTHESIS),
    Token(TokenType.NUMBER_LITERAL, firstLiteral),
    Token(firstOperation),
    Token(
        TokenType.NUMBER_LITERAL,
        secondLiteral,
    ),
    Token(TokenType.CLOSE_PARENTHESIS),
    Token(secondOperation),
    Token(
        TokenType.NUMBER_LITERAL,
        thirdLiteral,
    ),
    Token(TokenType.EOL),
)
