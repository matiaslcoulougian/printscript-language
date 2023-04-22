package unit

import ast.*
import ast.literalAST.BooleanAST
import ast.literalAST.StringAST
import org.junit.jupiter.api.Test
import printscript.language.parser.CompleteParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class IfParserTest {
    val parser = CompleteParser()

    @Test
    fun validateSimpleIfWithParentheses() {
        val tokens = listOf<Token>(
            Token(TokenType.IF),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.TRUE),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
        )

        assertEquals(parser.isValid(tokens), true)
    }

    @Test
    fun validateSimpleIfWithoutParentheses() {
        val tokens = listOf<Token>(
            Token(TokenType.IF),
            Token(TokenType.TRUE),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
        )

        assertEquals(parser.isValid(tokens), true)
    }

    @Test
    fun validateIfElse() {
        val tokens = listOf<Token>(
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
        )

        assertEquals(true, parser.isValid(tokens))
    }

    @Test
    fun parseIfTest() {
        val tokens = listOf<Token>(
            Token(TokenType.IF),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.TRUE),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
        )

        val condition = BooleanAST(true)
        val ifBlock = BlockAST(listOf(StringAST("Hello")))
        val expectedResult = IfAST(condition, ifBlock)
        assertEquals(expectedResult, parser.parseLine(tokens))
    }

    @Test
    fun parseIfElseTest() {
        val tokens = listOf<Token>(
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
        )

        val condition = BooleanAST(true)
        val ifBlock = BlockAST(listOf(StringAST("Hello")))
        val elseBlock = BlockAST(listOf(StringAST("Hello2")))
        val expectedResult = IfAST(condition, ifBlock, elseBlock)
        assertEquals(expectedResult, parser.parseLine(tokens))
    }
}
