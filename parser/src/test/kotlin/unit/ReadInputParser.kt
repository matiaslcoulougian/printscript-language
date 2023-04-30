package unit

import ast.InputAST
import ast.StringAST
import ast.SumAST
import org.junit.jupiter.api.Test
import printscript.language.parser.ReadInputParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class ReadInputParser {
    private val parser = ReadInputParser()

    @Test
    fun validateReadInputTest() {
        val validLine = listOf<Token>(
            Token(TokenType.READ_INPUT),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )

        val invalidLine = listOf<Token>(
            Token(TokenType.READ_INPUT),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.READ_INPUT),
            Token(TokenType.CLOSE_PARENTHESIS),
        )
        assertEquals(true, parser.isValidStatement(validLine))
        assertEquals(false, parser.isValidStatement(invalidLine))
    }

    @Test
    fun parseReadInputTest() {
        val line = listOf<Token>(
            Token(TokenType.READ_INPUT),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )
        val expectedResult = InputAST(SumAST(StringAST("world!"), StringAST("hello ")))
        val actualResult = parser.parseStatement(line)
        assertEquals(expectedResult, actualResult)
    }
}
