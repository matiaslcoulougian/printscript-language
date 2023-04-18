package printscript.language.parser

import ast.AST
import ast.AssignationAST
import ast.DeclarationAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class DeclarationParser() : LineParser {

//    Valid declarations
//    -> let a : String
//    -> let a : Number = <valid argument>

    override fun isValidDeclaration(tokens: List<Token>, parsers: List<LineParser>): Boolean {
        try {
            checkExceptions(tokens, parsers)
        } catch (e: Exception) {
            return false
        }
        return true
    }
    override fun parse(tokens: List<Token>, parsers: List<LineParser>): AST {
        val variableName = tokens[1].value
        val type = tokens[2].type
        val declaration = DeclarationAST(variableName, type)
        if (tokens.size > 3 && tokens[3].type == TokenType.EQUALS) {
            val validParser = parsers.find { it -> it.isValidDeclaration(tokens.subList(4, tokens.size)) }
            if (validParser != null) {
                return AssignationAST(declaration, validParser.parse(tokens.subList(4, tokens.size)))
            }
            throw Exception("Cant parse expression")
        }
        return declaration
    }

    private fun checkExceptions(tokens: List<Token>, parsers: List<LineParser>) {
        if (tokens[0].type != TokenType.VARIABLE) throw Exception("Invalid declaration")
        if (tokens[1].type != TokenType.IDENTIFIER) throw Exception("Not a variable name")
        if (tokens[2].type != TokenType.STRING_TYPE &&
            tokens[2].type != TokenType.NUMBER_TYPE
        ) {
            throw Exception("Invalid type")
        }
        if (tokens.size > 3 && tokens[3].type == TokenType.EQUALS) {
            val isExpressionValid = parsers.any { it.isValidDeclaration(tokens.subList(4, tokens.size)) }
            if (!isExpressionValid) throw Exception("Invalid assignation value")
        }
    }
}
