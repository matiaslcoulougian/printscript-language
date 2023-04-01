package unit
import ast.MulAST
import ast.NumberAST
import ast.SumAST
import ast.VariableAST
import org.junit.jupiter.api.Test
import printscript.language.parser.ShuntingYardParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class ParserTest {
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

        val parser = ShuntingYardParser()
        val result = parser.parse(infixExpression)

        // val expectedAST = SumAST(MulAST(MulAST(SumAST(NumberAST(5), NumberAST(3)), NumberAST(2)), NumberAST(2)), NumberAST(5))
        val expectedAST = SumAST(NumberAST(5.0), MulAST(NumberAST(2.0), MulAST(NumberAST(2.0), SumAST(NumberAST(3.0), NumberAST(5.0)))))
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

        val parser = ShuntingYardParser()
        val result = parser.parse(infixExpression)

        // val expectedAST = SumAST(MulAST(MulAST(SumAST(NumberAST(5), NumberAST(3)), NumberAST(2)), NumberAST(2)), NumberAST(5))
        val expectedAST = SumAST(VariableAST("b"), MulAST(NumberAST(2.0), MulAST(NumberAST(2.0), SumAST(VariableAST("a"), NumberAST(5.0)))))
        assertEquals(result, expectedAST)
    }
}
