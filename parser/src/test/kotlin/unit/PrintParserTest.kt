package unit
import ast.PrintAST
import ast.StringAST
import ast.SumAST
import printscript.language.parser.CompleteParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class PrintParserTest {
    @Test
    fun printStringTest() {
        val parser = CompleteParser()

        val line = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.EOL),
        )

        val expectedResult = PrintAST(StringAST("hello world!"))
        val actualResult = parser.parseLine(line)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun printConcatStringTest() {
        val parser = CompleteParser()

        val line = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.EOL),
        )
        val expectedResult = PrintAST(SumAST(StringAST("world!"), StringAST("hello ")))
        val actualResult = parser.parseLine(line)
        assertEquals(expectedResult, actualResult)
    }
}
