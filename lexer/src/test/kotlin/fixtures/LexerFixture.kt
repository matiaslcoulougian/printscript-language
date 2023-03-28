package fixtures

import lexer.Token
import lexer.TokenType

fun getLexStringVariableDeclarationResponse(identifier: String, literal: String): List<Token> = listOf(Token(TokenType.DESIGNATOR), Token(
    TokenType.IDENTIFIER, identifier), Token(TokenType.STRING_TYPE), Token(
    TokenType.EQUALS), Token(TokenType.STRING_LITERAL, literal), Token(TokenType.EOL), Token(
    TokenType.EOF))

fun getLexStringVariableWithDoubleQuoteResponse(identifier: String, literal: String): List<Token> = listOf(Token(TokenType.DESIGNATOR), Token(TokenType.IDENTIFIER, identifier), Token(TokenType.STRING_TYPE), Token(TokenType.EQUALS), Token(TokenType.STRING_LITERAL, literal), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexNumberVariableDeclarationResponse(identifier: String, literal: String): List<Token> = listOf(Token(TokenType.DESIGNATOR), Token(TokenType.IDENTIFIER, identifier), Token(TokenType.NUMBER_TYPE), Token(TokenType.EQUALS), Token(TokenType.NUMBER_LITERAL, literal), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexPrintLnFunctionResponse(literal: String): List<Token> = listOf(Token(TokenType.PRINTLN), Token(TokenType.OPEN_PARENTHESIS), Token(TokenType.STRING_LITERAL, literal), Token(TokenType.CLOSE_PARENTHESIS), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexPrintLnFunctionWithIdentifierResponse(identifier: String): List<Token> = listOf(Token(TokenType.PRINTLN), Token(TokenType.OPEN_PARENTHESIS), Token(TokenType.IDENTIFIER, identifier), Token(TokenType.CLOSE_PARENTHESIS), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexStringConcatenationResponse(firstLiteral: String, secondLiteral: String): List<Token> = listOf(Token(TokenType.STRING_LITERAL, firstLiteral), Token(TokenType.SUM), Token(TokenType.STRING_LITERAL, secondLiteral), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexStringConcatenationWithIdentifierResponse(identifier: String, literal: String): List<Token> = listOf(Token(TokenType.STRING_LITERAL, literal), Token(TokenType.SUM), Token(TokenType.IDENTIFIER, identifier), Token(TokenType.EOL), Token(TokenType.EOF))

fun geLexOperationResponse(firstLiteral: String, secondLiteral: String, operationType: TokenType): List<Token> = listOf(Token(TokenType.NUMBER_LITERAL, firstLiteral), Token(operationType), Token(TokenType.NUMBER_LITERAL, secondLiteral), Token(TokenType.EOL), Token(TokenType.EOF))

fun geLexOperationsWithParenthesisResponse(firstLiteral: String, secondLiteral: String, thirdLiteral: String, firstOperation: TokenType, secondOperation: TokenType): List<Token> = listOf(Token(TokenType.OPEN_PARENTHESIS), Token(TokenType.NUMBER_LITERAL, firstLiteral), Token(firstOperation), Token(TokenType.NUMBER_LITERAL, secondLiteral), Token(TokenType.CLOSE_PARENTHESIS), Token(secondOperation), Token(TokenType.NUMBER_LITERAL, thirdLiteral), Token(TokenType.EOL), Token(TokenType.EOF))
