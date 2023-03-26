package fixtures

import lexer.Token
import lexer.TokenType

// TODO: Refactor fixtures to receive custom params


fun getLexStringVariableDeclarationResponse(): List<Token> = listOf(Token(TokenType.DESIGNATOR), Token(
    TokenType.IDENTIFIER, "name"), Token(TokenType.STRING_TYPE), Token(
    TokenType.EQUALS), Token(TokenType.STRING_LITERAL, "Joe"), Token(TokenType.EOL), Token(
    TokenType.EOF))

fun getLexStringVariableWithDoubleQuoteResponse(): List<Token> = listOf(Token(TokenType.DESIGNATOR), Token(TokenType.IDENTIFIER, "name"), Token(TokenType.STRING_TYPE), Token(TokenType.EQUALS), Token(TokenType.STRING_LITERAL, "Joe"), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexNumberVariableDeclarationResponse(): List<Token> = listOf(Token(TokenType.DESIGNATOR), Token(TokenType.IDENTIFIER, "a"), Token(TokenType.NUMBER_TYPE), Token(TokenType.EQUALS), Token(TokenType.NUMBER_LITERAL, "12"), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexPrintLnFunctionResponse(): List<Token> = listOf(Token(TokenType.PRINTLN), Token(TokenType.STRING_LITERAL, "hello"), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexPrintLnFunctionWithIdentifierResponse(): List<Token> = listOf(Token(TokenType.PRINTLN), Token(TokenType.IDENTIFIER, "name"), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexStringConcatenationResponse(): List<Token> = listOf(Token(TokenType.STRING_LITERAL, "hello"), Token(TokenType.SUM), Token(TokenType.STRING_LITERAL, "world"), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexStringConcatenationWithIdentifierResponse(): List<Token> = listOf(Token(TokenType.STRING_LITERAL, "hello "), Token(TokenType.SUM), Token(TokenType.IDENTIFIER, "name"), Token(TokenType.EOL), Token(TokenType.EOF))

fun geLexSumOperationResponse(): List<Token> = listOf(Token(TokenType.NUMBER_LITERAL, "2"), Token(TokenType.SUM), Token(TokenType.NUMBER_LITERAL, "4"), Token(TokenType.EOL), Token(TokenType.EOF))
fun getLexSubtractionOperationResponse(): List<Token> = listOf(Token(TokenType.NUMBER_LITERAL, "2"), Token(TokenType.SUBTRACTION), Token(TokenType.NUMBER_LITERAL, "4"), Token(TokenType.EOL), Token(TokenType.EOF))
fun geLexProductOperationResponse(): List<Token> = listOf(Token(TokenType.NUMBER_LITERAL, "2"), Token(TokenType.PRODUCT), Token(TokenType.NUMBER_LITERAL, "4"), Token(TokenType.EOL), Token(TokenType.EOF))

fun getLexDivideOperationResponse(): List<Token> = listOf(Token(TokenType.NUMBER_LITERAL, "2"), Token(TokenType.DIVISION), Token(TokenType.NUMBER_LITERAL, "4"), Token(TokenType.EOL), Token(TokenType.EOF))


