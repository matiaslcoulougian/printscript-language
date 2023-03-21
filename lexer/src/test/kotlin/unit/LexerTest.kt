package unit

import fixtures.*
import lexer.Lexer
import lexer.Token
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LexerTest {
    var lexer = Lexer()
    fun testLexer(expectedTokens: List<Token>, inputString: String) {
        val resultTokens: List<Token> = lexer.getAllTokens(inputString)
        assertEquals(expectedTokens, resultTokens)
    }

    @Test
    fun testStringVariableDeclaration() {
        val expectedTokens = getLexStringVariableDeclarationResponse()
        val inputString = "let name: string = 'Joe';"

        testLexer(expectedTokens, inputString)
    }
    @Test
    fun testStringVariableDeclarationDoubleQuoted() {
        val expectedTokens = getLexStringVariableWithDoubleQuoteResponse()
        val inputString = "let name: string = \"Joe\";"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testNumberVariableDeclaration() {
        val expectedTokens = getLexNumberVariableDeclarationResponse()
        val inputString = "let a: number = 12;"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testPrintLnFunction() {
        val expectedTokens = getLexPrintLnFunctionResponse()
        val inputString = "println('hello');"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testPrintLnFunctionWithIdentifier() {
        val expectedTokens = getLexPrintLnFunctionWithIdentifierResponse()
        val inputString = "println(name);"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testStringConcatenation() {
        val expectedTokens = getLexStringConcatenationResponse()
        val inputString = "'hello' + 'world';"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testStringConcatenationWithIdentifier() {
        val expectedTokens = getLexStringConcatenationWithIdentifierResponse()
        val inputString = "'hello ' + name;"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testSumOperator() {
        val expectedTokens = geLexSumOperationResponse()
        val inputString = "2 + 4;"
        testLexer(expectedTokens, inputString)
    }

//    @Test
//    fun testSubtractionOperator() {
//        val expectedTokens = listOf(Token("2", TokenType.number_literal), Token("-", TokenType.operator), Token("4", TokenType.number_literal), Token(";", TokenType.end))
//        val inputString = "2 - 4;"
//        testLexer(expectedTokens, inputString)
//    }
//
//    @Test
//    fun testProductOperator() {
//        val expectedTokens = listOf(Token("2", TokenType.number_literal), Token("*", TokenType.operator), Token("4", TokenType.number_literal), Token(";", TokenType.end))
//        val inputString = "2 * 4;"
//        testLexer(expectedTokens, inputString)
//    }

    @Test
    fun testDivisionOperator() {
        val expectedTokens = getLexDivideOperationResponse()
        val inputString = "2/4;"
        testLexer(expectedTokens, inputString)
    }
}