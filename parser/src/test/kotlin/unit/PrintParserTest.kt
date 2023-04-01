package unit
import ast.PrintAST
import ast.StringAST
import ast.SumAST
import printscript.language.parser.Parser
import printscript.language.parser.PrintParser
import printscript.language.parser.ShuntingYardParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class PrintParserTest {
    @Test
    fun printStringTest() {
        val printParser = PrintParser()
        val shuntingYardParser = ShuntingYardParser()
        val parser = Parser(listOf(printParser, shuntingYardParser))

        val line = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )

        val expectedResult = PrintAST(StringAST("hello world!"))
        val actualResult = parser.parseLine(line)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun printConcatStringTest() {
        val printParser = PrintParser()
        val shuntingYardParser = ShuntingYardParser()
        val parser = Parser(listOf(printParser, shuntingYardParser))

        val line = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )
        val expectedResult = PrintAST(SumAST(StringAST("world!"), StringAST("hello ")))
        val actualResult = parser.parseLine(line)
        assertEquals(expectedResult, actualResult)
    }
}
