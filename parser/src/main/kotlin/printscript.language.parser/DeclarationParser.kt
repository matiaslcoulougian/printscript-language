package printscript.language.parser

import Type
import ast.AST
import ast.AssignationAST
import ast.DeclarationAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class DeclarationParser() : StatementParser {

//    Valid declarations
//    -> let a : String;
//    -> let a : Number = <valid argument>;

    override fun isValidStatement(tokens: List<Token>): Boolean = tokens[0].type == TokenType.CONSTANT || tokens[0].type == TokenType.VARIABLE

    override fun parseStatement(tokens: List<Token>): AST {
        checkExceptions(tokens)
        val variableName = tokens[1].value
        val type = tokens[2].type
        val declaration = DeclarationAST(variableName, getType(type), isConstant(tokens[0]), tokens[1].line, tokens[1].column)
        if (tokens.size > 3 && tokens[3].type == TokenType.EQUALS) { // has an assignation
            val statementParser = StatementParser()
            val statement = statementParser.parseLine(tokens.subList(4, tokens.size - 1))
            return AssignationAST(declaration, statement, tokens[4].line, tokens[4].column)
        }
        return declaration
    }

    private fun getType(tokenType: TokenType): Type {
        return when (tokenType) {
            TokenType.STRING_TYPE -> Type.STRING
            TokenType.NUMBER_TYPE -> Type.NUMBER
            TokenType.BOOLEAN_TYPE -> Type.BOOLEAN
            else -> Type.UNDEFINED
        }
    }

    private fun isConstant(token: Token): Boolean {
        return token.type === TokenType.CONSTANT
    }

    private fun checkExceptions(tokens: List<Token>) {
        if (tokens[0].type != TokenType.CONSTANT && tokens[0].type != TokenType.VARIABLE) throw Exception("invalid declaration")
        if (tokens[1].type != TokenType.IDENTIFIER) throw Exception("Not a variable name in line ${tokens[1].line} column ${tokens[1].column}")
        if (tokens[2].type != TokenType.STRING_TYPE &&
            tokens[2].type != TokenType.NUMBER_TYPE &&
            tokens[2].type != TokenType.BOOLEAN_TYPE
        ) {
            throw Exception("Invalid type in line ${tokens[2].line} column ${tokens[2].column}. ${tokens[2].value} is not a valid type.")
        }
        if (tokens[tokens.size - 1].type != TokenType.EOL) throw Exception("Missing semicolon in line ${tokens[tokens.size - 1].line} column ${tokens[tokens.size - 1].column}")
        if (tokens.size > 3 && tokens[3].type == TokenType.EQUALS) {
            val statementParser = StatementParser()
            val isExpressionValid = statementParser.isValid(tokens.subList(4, tokens.size - 1))
            if (!isExpressionValid) throw Exception("Invalid declaration value in line ${tokens[4].line} column ${tokens[4].column}")
        }
    }
}
