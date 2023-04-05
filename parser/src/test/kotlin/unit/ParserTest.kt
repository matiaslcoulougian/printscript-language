package unit
import ast.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test
import printscript.language.parser.CompleteParser
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
            Token(TokenType.EOL),
            Token(TokenType.EOF),
        )

        val parser = CompleteParser()
        val result = parser.parse(infixExpression)

        // val expectedAST = SumAST(MulAST(MulAST(SumAST(NumberAST(5), NumberAST(3)), NumberAST(2)), NumberAST(2)), NumberAST(5))
        val expectedAST = listOf<AST>(SumAST(NumberAST(5.0), MulAST(NumberAST(2.0), MulAST(NumberAST(2.0), SumAST(NumberAST(3.0), NumberAST(5.0))))))
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
            Token(TokenType.EOL),
            Token(TokenType.EOF),
        )

        val parser = CompleteParser()
        val result = parser.parse(infixExpression)

        // val expectedAST = SumAST(MulAST(MulAST(SumAST(NumberAST(5), NumberAST(3)), NumberAST(2)), NumberAST(2)), NumberAST(5))
        val expectedAST = listOf<AST>(SumAST(VariableAST("b"), MulAST(NumberAST(2.0), MulAST(NumberAST(2.0), SumAST(VariableAST("a"), NumberAST(5.0))))))
        assertEquals(result, expectedAST)
    }
}
