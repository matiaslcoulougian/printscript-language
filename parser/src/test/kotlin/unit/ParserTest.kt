package unit

import ast.*
import org.junit.jupiter.api.Test
import printscript.language.parser.CompleteParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class ParserTest {
    private val parser = CompleteParser()

    @Test
    fun isValidTest() {
        val validLines = listOf<Token>(
            Token(TokenType.IF),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.TRUE),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.ELSE),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.EOL),
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )

        val invalidLines = listOf<Token>(
            Token(TokenType.IF),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.TRUE),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.ELSE),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )
        assertEquals(true, parser.isValid(validLines))
        assertEquals(false, parser.isValid(invalidLines))
    }

    @Test
    fun parseManyLinesTest() {
        val validLines = listOf<Token>(
            Token(TokenType.IF),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.TRUE),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.ELSE),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello2"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.EOL),
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )

        val condition = BooleanAST(true)
        val ifBlock = BlockAST(listOf(StringAST("Hello")))
        val elseBlock = BlockAST(listOf(StringAST("Hello2")))
        val expectedResult = listOf<AST>(IfAST(condition, ifBlock, elseBlock), PrintAST(SumAST(StringAST("world!"), StringAST("hello "))))
        assertEquals(expectedResult, parser.parse(validLines))
    }

    @Test
    fun parseLineTest() {
        val validLines = listOf<Token>(
            Token(TokenType.PRINTLN),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "hello "),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "world!"),
            Token(TokenType.CLOSE_PARENTHESIS),
        )

        val expectedResult = PrintAST(SumAST(StringAST("world!"), StringAST("hello ")))
        assertEquals(expectedResult, parser.parseLine(validLines))
    }
}
