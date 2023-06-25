package printscript.language.parser

import ast.AST
import ast.AssignationAST
import ast.VariableAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class AssignationParser : StatementParser {
/*    can parse statements like:
    x = 5;
    x = 5 * 5;
    x = true;
    x = "hello";

    [IDENTIFIER] [EQUALS] [NUMBER_LITERAL | OPERATION | STRING_LITERAL | IDENTIFIER | BOOLEAN_LITERAL]
 */
    override fun isValidStatement(tokens: List<Token>): Boolean =
        tokens[0].type == TokenType.IDENTIFIER && tokens[1].type == TokenType.EQUALS

    override fun parseStatement(tokens: List<Token>): AST {
        checkExceptions(tokens)
        val identifierName = tokens[0].value

        val parser = StatementParser()
        val value = parser.parseLine(tokens.subList(2, tokens.size - 1))

        return AssignationAST(VariableAST(identifierName, tokens[0].line, tokens[0].column), value, tokens[2].line, tokens[2].column)
    }

    private fun checkExceptions(tokens: List<Token>) {
        if (tokens[tokens.size - 1].type != TokenType.EOL) throw Exception("Missing semicolon in line ${tokens[tokens.size - 1].line} column ${tokens[tokens.size - 1].column}")
        val parser = StatementParser()
        val isExpressionValid = parser.isValid(tokens.subList(2, tokens.size - 1))
        if (!isExpressionValid) throw Exception("Invalid declaration value in line ${tokens[2].line} column ${tokens[2].column}")
    }
}
