package unit

import ast.AssignationAST
import ast.BooleanAST
import ast.NumberAST
import ast.StringAST
import ast.VariableAST
import org.junit.jupiter.api.Test
import printscript.language.parser.AssignationParser
import printscript.language.token.Token
import printscript.language.token.TokenType
import kotlin.test.assertEquals

class AssignationParserTest {
    val parser = AssignationParser()

    @Test
    fun numberAssignationTest() {
        val numberAssignation = listOf<Token>(
            Token(TokenType.IDENTIFIER, "x"),
            Token(TokenType.EQUALS),
            Token(TokenType.NUMBER_LITERAL, "5"),
        )

        val expectedResult = AssignationAST(VariableAST("x"), NumberAST(5.0))
        val actualResult = parser.parseStatement(numberAssignation)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun stringAssignationTest() {
        val numberAssignation = listOf<Token>(
            Token(TokenType.IDENTIFIER, "x"),
            Token(TokenType.EQUALS),
            Token(TokenType.STRING_LITERAL, "hello"),
        )

        val expectedResult = AssignationAST(VariableAST("x"), StringAST("hello"))
        val actualResult = parser.parseStatement(numberAssignation)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun booleanAssignationTest() {
        val numberAssignation = listOf<Token>(
            Token(TokenType.IDENTIFIER, "x"),
            Token(TokenType.EQUALS),
            Token(TokenType.TRUE),
        )

        val expectedResult = AssignationAST(VariableAST("x"), BooleanAST(true))
        val actualResult = parser.parseStatement(numberAssignation)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun variableAssignationTest() {
        val numberAssignation = listOf<Token>(
            Token(TokenType.IDENTIFIER, "x"),
            Token(TokenType.EQUALS),
            Token(TokenType.IDENTIFIER, "y"),
        )

        val expectedResult = AssignationAST(VariableAST("x"), VariableAST("y"))
        val actualResult = parser.parseStatement(numberAssignation)

        assertEquals(expectedResult, actualResult)
    }
}
