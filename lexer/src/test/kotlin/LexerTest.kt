import org.junit.jupiter.api.Test
import printscript.language.lexer.Lexer
import printscript.language.lexer.LexerFactory
import printscript.language.lexer.TokenListIterator
import printscript.language.token.Token
import printscript.language.token.TokenType
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.test.assertEquals

class LexerTest {

    private fun testStatement(lexer: Lexer, expectedTokens: List<Token>, inputString: String) {
        val resultTokens: List<Token> = lexer.getTokens(listOf(inputString), 0)
        assertEquals(expectedTokens, resultTokens)
    }

    @Test
    fun testLexer() {
        val testFileInputStream = FileInputStream("src/test/resources/lexer-test.txt")
        val lexer = LexerFactory().createLexer("1.1", testFileInputStream)
        val testFileReader = BufferedReader(InputStreamReader(testFileInputStream))
        var line = testFileReader.readLine()
        while (line != null) {
            val testParts = line.split("~")
            val inputString = testParts[0]
            val resultTokens = testParts[1]
            val expectedTokens = getExpectedTokensListWithLine(resultTokens, 1)
            testStatement(lexer, expectedTokens, inputString)
            line = testFileReader.readLine()
        }
        testFileReader.close()
    }

    private fun getExpectedTokensListWithLine(resultLine: String, lineNumber: Int): List<Token> {
        return resultLine
            .split("|")
            .map { tokenParams ->
                val params = tokenParams.split(",")
                Token(TokenType.valueOf(params[0]), lineNumber, params[1].toInt(), params[2])
            }
            .toList()
    }

    private fun getExpectedTokensList(resultLine: String): List<Token> {
        return resultLine
            .split("|")
            .map { tokenParams ->
                val params = tokenParams.split(",")
                Token(TokenType.valueOf(params[0]), params[1].toInt(), params[2].toInt(), params[3])
            }
            .toList()
    }

    @Test
    fun testLexerWithIterator() {
        val fileInputStream = FileInputStream("src/test/resources/printscript-file.txt")
        val lexer = LexerFactory().createLexer("1.1", fileInputStream)
        val tokenListIterator = TokenListIterator(lexer)
        val resultFileInputStream = FileInputStream("src/test/resources/printscript-file-tokens.txt")
        val resultFileReader = BufferedReader(InputStreamReader(resultFileInputStream))
        var resultLine = resultFileReader.readLine()
        while (resultLine != null) {
            val expectedTokens = getExpectedTokensList(resultLine)
            val resultTokens = tokenListIterator.next()
            assertEquals(expectedTokens, resultTokens)
            resultLine = resultFileReader.readLine()
        }
    }
}
