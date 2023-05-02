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
    override fun isValidStatement(tokens: List<Token>): Boolean {
        if (tokens[0].type != TokenType.IDENTIFIER) return false
        if (tokens[1].type != TokenType.EQUALS) return false
        if (tokens[tokens.size - 1].type != TokenType.EOL) return false

        val parser = StatementParser()
        return parser.isValid(tokens.subList(2, tokens.size))
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val identifierName = tokens[0].value

        val parser = StatementParser()
        val value = parser.parseLine(tokens.subList(2, tokens.size - 1))

        return AssignationAST(VariableAST(identifierName, tokens[0].line, tokens[0].column), value, tokens[2].line, tokens[2].column)
    }
}
