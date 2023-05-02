package unit
import ast.PrintAST
import ast.StringAST
import ast.SumAST
import printscript.language.parser.PrintParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class PrintParserTest {

    private val parser = PrintParser()

    @Test
    fun parserPrintTest() {
        val line = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )

        val expectedResult = PrintAST(StringAST("hello world!"))
        val actualResult = parser.parseStatement(line)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun printConcatStringTest() {
        val line = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )
        val expectedResult = PrintAST(SumAST(StringAST("world!"), StringAST("hello ")))
        val actualResult = parser.parseStatement(line)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun validateStringTest() {
        val validLine = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )

        val invalidLine = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.PRINTLN),
            Token(TokenType.CLOSE_PARENTHESIS),
        )
        assertEquals(true, parser.isValidStatement(validLine))
        assertEquals(false, parser.isValidStatement(invalidLine))
    }
}
