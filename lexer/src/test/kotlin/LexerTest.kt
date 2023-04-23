import org.junit.jupiter.api.Test
import printscript.language.lexer.LexerFactory
import printscript.language.token.Token
import printscript.language.token.TokenType
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.test.assertEquals

class LexerTest {
    private var fileInputStream = FileInputStream("src/test/resources/lexer-test.txt")
    private var lexer = LexerFactory().createLexer("1.1", fileInputStream)
    private val testFileReader = BufferedReader(InputStreamReader(fileInputStream))

    private fun testStatement(expectedTokens: List<Token>, inputString: String) {
        val resultTokens: List<Token> = lexer.getTokens(inputString, 1)
        assertEquals(expectedTokens, resultTokens)
    }

    @Test
    fun testLexer() {
        var line = testFileReader.readLine()
        while (line != null) {
            val testParts = line.split("~")
            val inputString = testParts[0]
            val expectedTokens = getExpectedTokensList(testParts)
            testStatement(expectedTokens, inputString)
            line = testFileReader.readLine()
        }
        testFileReader.close()
    }

    private fun getExpectedTokensList(testParts: List<String>): List<Token> {
        return testParts[1]
            .split("|")
            .map { tokenParams ->
                val params = tokenParams.split(",")
                Token(TokenType.valueOf(params[0]), 1, params[1].toInt(), params[2])
            }
            .toList()
    }
}
