package unit

import ast.BlockAST
import ast.BooleanAST
import ast.IfAST
import ast.StringAST
import org.junit.jupiter.api.Test
import printscript.language.parser.BlockParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class BlockParserTest {
    val blockParser = BlockParser()

    @Test
    fun testValidBlock() {
        val tokens = listOf<Token>(
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.IF),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.TRUE),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.CLOSE_BLOCK),
        )

        assertEquals(true, blockParser.isValidStatement(tokens))
    }

    @Test
    fun testInvalidBlock() {
        val tokens = listOf<Token>(
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.IF),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.TRUE),
            Token(TokenType.CLOSE_PARENTHESIS),
            Token(TokenType.OPEN_BLOCK),
            Token(TokenType.OPEN_PARENTHESIS),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.SUM),
            Token(TokenType.SUM),
            Token(TokenType.STRING_LITERAL, "Hello"),
            Token(TokenType.CLOSE_BLOCK),
            Token(TokenType.CLOSE_BLOCK),
        )

        assertEquals(false, blockParser.isValidStatement(tokens))
    }

    @Test
    fun parseValidBlockTest() {
        val tokens = listOf<Token>(
            Token(TokenType.OPEN_BLOCK),
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
            Token(TokenType.CLOSE_BLOCK),
        )

        val condition = BooleanAST(true)
        val ifBlock = BlockAST(listOf(StringAST("Hello")))
        val elseBlock = BlockAST(listOf(StringAST("Hello2")))
        val expectedResult = BlockAST(listOf(IfAST(condition, ifBlock, elseBlock)))
        assertEquals(expectedResult, blockParser.parseStatement(tokens))
    }
}
