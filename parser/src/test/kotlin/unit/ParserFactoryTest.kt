package unit

import org.junit.jupiter.api.Test
import printscript.language.parser.ParserFactory
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class ParserFactoryTest {
    private val factory = ParserFactory()
    val parser1 = factory.createParser("1.0")
    val parser2 = factory.createParser("1.1")

    @Test
    fun validExpressionsTest() {
        val ifTokens = listOf<Token>(
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

        assertEquals(true, parser2.isValid(ifTokens))
        assertEquals(false, parser1.isValid(ifTokens))
    }
}
