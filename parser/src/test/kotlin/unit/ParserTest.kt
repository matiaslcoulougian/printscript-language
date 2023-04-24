package unit
import ast.* // ktlint-disable no-wildcard-imports
import ast.NumberAST
import ast.StringAST
import org.junit.jupiter.api.Test
import printscript.language.parser.ShuntingYardParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class ParserTest {
    val parser = ShuntingYardParser()

    @Test
    fun shuntingYardParserTest() {
        // "(5 + 3) * 2 * 2 + 5"
        val infixExpression = listOf<Token>(
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.NUMBER_LITERAL, "5"),
            Token(TokenType.SUM),
            Token(TokenType.NUMBER_LITERAL, "3"),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.PRODUCT),
            Token(TokenType.NUMBER_LITERAL, "2"),
            Token(TokenType.PRODUCT),
            Token(TokenType.NUMBER_LITERAL, "2"),
            Token(TokenType.SUM),
            Token(TokenType.NUMBER_LITERAL, "5"),
        )

        val result = parser.parseStatement(infixExpression)

        // val expectedAST = SumAST(MulAST(MulAST(SumAST(NumberAST(5), NumberAST(3)), NumberAST(2)), NumberAST(2)), NumberAST(5))
        val expectedAST =
            SumAST(
                NumberAST(5.0),
                MulAST(
                    NumberAST(2.0),
                    MulAST(
                        NumberAST(2.0),
                        SumAST(
                            NumberAST(3.0),
                            NumberAST(5.0),
                        ),
                    ),
                ),
            )

        assertEquals(result, expectedAST)
    }

    @Test
    fun shuntingYardWithVariables() {
        // "(5 + a) * 2 * 2 + b"
        val infixExpression = listOf<Token>(
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.NUMBER_LITERAL, "5"),
            Token(TokenType.SUM),
            Token(TokenType.IDENTIFIER, "a"),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.PRODUCT),
            Token(TokenType.NUMBER_LITERAL, "2"),
            Token(TokenType.PRODUCT),
            Token(TokenType.NUMBER_LITERAL, "2"),
            Token(TokenType.SUM),
            Token(TokenType.IDENTIFIER, "b"),
        )

        val result = parser.parseStatement(infixExpression)

        // val expectedAST = SumAST(MulAST(MulAST(SumAST(NumberAST(5), NumberAST(3)), NumberAST(2)), NumberAST(2)), NumberAST(5))
        val expectedAST = SumAST(VariableAST("b"), MulAST(NumberAST(2.0), MulAST(NumberAST(2.0), SumAST(VariableAST("a"), NumberAST(5.0)))))
        assertEquals(result, expectedAST)
    }

    @Test
    fun shuntingYardParseLiterals() {
        val numberLiteral = listOf<Token>(Token(TokenType.NUMBER_LITERAL, "5"))
        val numberOperation = listOf<Token>(
            Token(TokenType.NUMBER_LITERAL, "5"),
            Token(TokenType.SUM),
            Token(TokenType.NUMBER_LITERAL, "5"),
        )
        val stringLiteral = listOf<Token>(Token(TokenType.STRING_LITERAL, "hello"))
        val stringOperation = listOf<Token>(
            Token(TokenType.STRING_LITERAL, "hello"),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, " world"),
        )

        val variable = listOf<Token>(Token(TokenType.IDENTIFIER, "x"))
        val variableOperation = listOf<Token>(
            Token(TokenType.IDENTIFIER, "x"),
            Token(TokenType.SUM),
            Token(TokenType.IDENTIFIER, "y"),
        )
        val variableNumberOperation = listOf<Token>(
            Token(TokenType.IDENTIFIER, "x"),
            Token(TokenType.SUM),
            Token(TokenType.NUMBER_LITERAL, "5"),
        )
        val variableStringOperation = listOf<Token>(
            Token(TokenType.STRING_LITERAL, "hello"),
            Token(TokenType.SUM),
            Token(TokenType.IDENTIFIER, "x"),
        )

        assertEquals(parser.parseStatement(numberLiteral), NumberAST(5.0))
        assertEquals(parser.parseStatement(numberOperation), SumAST(NumberAST(5.0), NumberAST(5.0)))
        assertEquals(parser.parseStatement(stringLiteral), StringAST("hello"))
        assertEquals(parser.parseStatement(stringOperation), SumAST(StringAST(" world"), StringAST("hello")))
        assertEquals(parser.parseStatement(variable), VariableAST("x"))
        assertEquals(parser.parseStatement(variableOperation), SumAST(VariableAST("y"), VariableAST("x")))
        assertEquals(parser.parseStatement(variableNumberOperation), SumAST(NumberAST(5.0), VariableAST("x")))
        assertEquals(parser.parseStatement(variableStringOperation), SumAST(VariableAST("x"), StringAST("hello")))
    }
}
