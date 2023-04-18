package unit

import ast.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test
import printscript.language.parser.CompleteParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class DeclarationParserTest {
    @Test
    fun parseStringDeclarationTest() {
        val parser = CompleteParser()

        val line = listOf(
            Token(TokenType.VARIABLE),
            Token(
                TokenType.IDENTIFIER,
                "example",
            ),
            Token(TokenType.STRING_TYPE),
            Token(
                TokenType.EQUALS,
            ),
            Token(TokenType.STRING_LITERAL, "This is a test text"),
        )

        val actualResult = parser.parseLine(line)
        val expectedResult = AssignationAST(DeclarationAST("example", TokenType.STRING_TYPE), StringAST("This is a test text"))
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun parseSumDeclarationTest() {
        val parser = CompleteParser()

        val line = listOf(
            Token(TokenType.VARIABLE),
            Token(
                TokenType.IDENTIFIER,
                "example",
            ),
            Token(TokenType.NUMBER_TYPE),
            Token(
                TokenType.EQUALS,
            ),
            Token(TokenType.NUMBER_LITERAL, "3"),
            Token(TokenType.SUM),
            Token(TokenType.NUMBER_LITERAL, "5"),
        )

        val actualResult = parser.parseLine(line)
        val expectedResult = AssignationAST(DeclarationAST("example", TokenType.NUMBER_TYPE), SumAST(NumberAST(5.0), NumberAST(3.0)))
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun parseJustDeclarationTest() {
        val parser = CompleteParser()

        val line = listOf(
            Token(TokenType.VARIABLE),
            Token(
                TokenType.IDENTIFIER,
                "example",
            ),
            Token(TokenType.NUMBER_TYPE),
        )

        val actualResult = parser.parseLine(line)
        val expectedResult = DeclarationAST("example", TokenType.NUMBER_TYPE)
        assertEquals(expectedResult, actualResult)
    }
}
