package fixtures

import lexer.Token
import lexer.TokenType

fun getLexStringVariableDeclarationResponse(): List<Token> = listOf(Token(TokenType.DESIGNATOR.name, "let"), Token(
    TokenType.IDENTIFIER.name, "name"), Token(TokenType.OPERATION.name, ":"), Token(TokenType.STRING_TYPE.name, "string"), Token(
    TokenType.OPERATION.name, "="), Token(TokenType.STRING_LITERAL.name, "Joe"), Token(TokenType.EOL.name, ""), Token(
    TokenType.EOF.name, ""))

fun getLexStringVariableWithDoubleQuoteResponse(): List<Token> = listOf(Token(TokenType.DESIGNATOR.name, "let"), Token(TokenType.IDENTIFIER.name, "name"), Token(TokenType.OPERATION.name, ":"), Token(TokenType.STRING_TYPE.name, "string"), Token(TokenType.OPERATION.name, "="), Token(TokenType.STRING_LITERAL.name, "Joe"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))

fun getLexNumberVariableDeclarationResponse(): List<Token> = listOf(Token(TokenType.DESIGNATOR.name, "let"), Token(TokenType.IDENTIFIER.name, "a"), Token(TokenType.OPERATION.name, ":"), Token(TokenType.NUMBER_TYPE.name, "number"), Token(TokenType.OPERATION.name, "="), Token(TokenType.NUMBER_LITERAL.name, "12"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))

fun getLexPrintLnFunctionResponse(): List<Token> = listOf(Token(TokenType.PRINTLN.name, "println"), Token(TokenType.STRING_LITERAL.name, "hello"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))

fun getLexPrintLnFunctionWithIdentifierResponse(): List<Token> = listOf(Token(TokenType.PRINTLN.name, "println"), Token(TokenType.IDENTIFIER.name, "name"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))

fun getLexStringConcatenationResponse(): List<Token> = listOf(Token(TokenType.STRING_LITERAL.name, "hello"), Token(TokenType.OPERATION.name, "+"), Token(TokenType.STRING_LITERAL.name, "world"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))

fun getLexStringConcatenationWithIdentifierResponse(): List<Token> = listOf(Token(TokenType.STRING_LITERAL.name, "hello "), Token(TokenType.OPERATION.name, "+"), Token(TokenType.IDENTIFIER.name, "name"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))

fun geLexSumOperationResponse(): List<Token> = listOf(Token(TokenType.NUMBER_LITERAL.name, "2"), Token(TokenType.OPERATION.name, "+"), Token(TokenType.NUMBER_LITERAL.name, "4"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))

fun getLexDivideOperationResponse(): List<Token> = listOf(Token(TokenType.NUMBER_LITERAL.name, "2"), Token(TokenType.OPERATION.name, "/"), Token(TokenType.NUMBER_LITERAL.name, "4"), Token(TokenType.EOL.name, ""), Token(TokenType.EOF.name, ""))


