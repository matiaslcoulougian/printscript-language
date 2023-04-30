package unit

import ast.BooleanAST
import org.junit.jupiter.api.Test
import printscript.language.parser.BooleanParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class BooleanParserTest {
    val parser = BooleanParser()

    @Test
    fun parseBooleanTest() {
        val trueBoolean = listOf<Token>(Token(TokenType.TRUE))
        val falseBoolean = listOf<Token>(Token(TokenType.FALSE))

        val trueExpected = BooleanAST(true)
        val falseExpected = BooleanAST(false)

        val trueResult = parser.parseStatement(trueBoolean)
        val falseResult = parser.parseStatement(falseBoolean)

        assertEquals(trueExpected, trueResult)
        assertEquals(falseExpected, falseResult)
    }

    @Test
    fun booleanValidationTest() {
        val trueBoolean = listOf<Token>(Token(TokenType.TRUE))
        val falseBoolean = listOf<Token>(Token(TokenType.FALSE))
        val invalidBoolean = listOf<Token>(Token(TokenType.IDENTIFIER, "true"))

        val trueResult = parser.parseStatement(trueBoolean)
        val falseResult = parser.parseStatement(falseBoolean)

        assertEquals(true, parser.isValidStatement(trueBoolean))
        assertEquals(true, parser.isValidStatement(falseBoolean))
        assertEquals(false, parser.isValidStatement(invalidBoolean))
    }
}
