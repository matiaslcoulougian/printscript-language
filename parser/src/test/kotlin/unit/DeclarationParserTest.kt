package unit

import Type
import ast.* // ktlint-disable no-wildcard-imports
import ast.NumberAST
import ast.StringAST
import org.junit.jupiter.api.Test
import printscript.language.parser.DeclarationParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class DeclarationParserTest {
    private val parser = DeclarationParser()

    @Test
    fun parseStringDeclarationTest() {
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
            Token(TokenType.EOL),
        )

        val actualResult = parser.parseStatement(line)
        val expectedResult = AssignationAST(DeclarationAST("example", Type.STRING), StringAST("This is a test text"))
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun parseSumDeclarationTest() {
        val line = listOf(
            Token(TokenType.CONSTANT),
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
            Token(TokenType.EOL),
        )

        val actualResult = parser.parseStatement(line)
        val expectedResult = AssignationAST(
            DeclarationAST("example", Type.NUMBER, true),
            SumAST(
                NumberAST(5.0),
                NumberAST(3.0),
            ),
        )
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun parseJustDeclarationTest() {
        val line = listOf(
            Token(TokenType.VARIABLE),
            Token(
                TokenType.IDENTIFIER,
                "example",
            ),
            Token(TokenType.NUMBER_TYPE),
            Token(TokenType.EOL),
        )

        val actualResult = parser.parseStatement(line)
        val expectedResult = DeclarationAST("example", Type.NUMBER)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun declarationValidationTest() {
        val validLine = listOf(
            Token(TokenType.VARIABLE),
            Token(
                TokenType.IDENTIFIER,
                "example",
            ),
            Token(TokenType.NUMBER_TYPE),
            Token(TokenType.EOL),
        )
        val invalidLine = listOf(
            Token(TokenType.NUMBER_TYPE),
            Token(TokenType.EOL),
        )

        assertEquals(true, parser.isValidStatement(validLine))
        assertEquals(false, parser.isValidStatement(invalidLine))
    }
}
