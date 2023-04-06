package unit

import fixtures.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test
import printscript.language.lexer.Lexer
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class LexerTest {
    var lexer = Lexer()
    fun testLexer(expectedTokens: List<Token>, inputString: String) {
        val resultTokens: List<Token> = lexer.getTokens(inputString)
        assertEquals(expectedTokens, resultTokens)
    }

    @Test
    fun testStringVariableDeclaration() {
        val expectedTokens = getLexStringVariableDeclarationResponse("name", "Joe")
        val inputString = "let name: string = 'Joe';"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testStringVariableDeclarationDoubleQuoted() {
        val expectedTokens = getLexStringVariableWithDoubleQuoteResponse("name", "Joe")
        val inputString = "let name: string = \"Joe\";"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testNumberVariableDeclaration() {
        val expectedTokens = getLexNumberVariableDeclarationResponse("a", "12")
        val inputString = "let a: number = 12;"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testPrintLnFunction() {
        val expectedTokens = getLexPrintLnFunctionResponse("hello")
        val inputString = "println('hello');"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testPrintLnFunctionWithIdentifier() {
        val expectedTokens = getLexPrintLnFunctionWithIdentifierResponse("name")
        val inputString = "println(name);"

        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testStringConcatenation() {
        val expectedTokens = getLexStringConcatenationResponse("hello", "world")
        val inputString = "'hello' + 'world';"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testStringConcatenationWithIdentifier() {
        val expectedTokens = getLexStringConcatenationWithIdentifierResponse("name", "hello ")
        val inputString = "'hello ' + name;"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testSumOperator() {
        val expectedTokens = geLexOperationResponse("2", "4", TokenType.SUM)
        val inputString = "2 + 4;"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testSubtractionOperator() {
        val expectedTokens = geLexOperationResponse("2", "4", TokenType.SUBTRACTION)
        val inputString = "2 - 4;"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testProductOperator() {
        val expectedTokens = geLexOperationResponse("2", "4", TokenType.PRODUCT)
        val inputString = "2 * 4;"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testDivisionOperator() {
        val expectedTokens = geLexOperationResponse("2", "4", TokenType.DIVISION)
        val inputString = "2/4;"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testOperationWithParenthesis() {
        val expectedTokens = geLexOperationsWithParenthesisResponse("2", "4", "6", TokenType.SUM, TokenType.SUBTRACTION)
        val inputString = "(2 + 4) - 6;"
        testLexer(expectedTokens, inputString)
    }

    @Test
    fun testConstDeclaration() {
        val expectedTokens = getLexConstDeclarationResponse("Joe", "name")
        val inputString = "const name: string = 'Joe';"
        testLexer(expectedTokens, inputString)
    }
}
