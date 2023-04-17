package printscript.language.parser

import ast.AST
import ast.IfAST
import ast.VariableAST
import ast.literalAST.BooleanAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class IfParser : LineParser {
    override fun isValidDeclaration(tokens: List<Token>, parsers: List<LineParser>): Boolean {
        return try {
            checkExceptions(tokens, parsers)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    override fun parse(tokens: List<Token>, parsers: List<LineParser>): AST {
        val condition: AST =
            return IfAST
    }

    private fun getCondition(token: Token): AST {
        return when (token.type) {
            TokenType.IDENTIFIER -> VariableAST(token.value)
            TokenType.TRUE -> BooleanAST(true)
            TokenType.FALSE -> BooleanAST(false)
            else -> throw Exception("can not parse condition")
        }
    }

    private fun checkExceptions(tokens: List<Token>, parsers: List<LineParser>) {
        if (tokens[0].type != TokenType.IF) throw Exception("invalid declaration")
        if (tokens[1].type != TokenType.VARIABLE &&
            tokens[1].type != TokenType.TRUE &&
            tokens[1].type != TokenType.FALSE
        ) {
            throw Exception("Invalid boolean argument")
        }
        if (tokens[2].type != TokenType.OPEN_BLOCK) { throw Exception("No block for if statement") }

        val elseIndex = elseIndex(tokens)
        if (elseIndex > 0) { // when the if has else
            if (tokens[elseIndex - 1].type != TokenType.CLOSE_BLOCK) throw Exception("did not close block before else")
            if (tokens[elseIndex + 1].type != TokenType.OPEN_BLOCK) throw Exception("did not open block after else expression")
            val isFirstBlockValid = parsers.any { it.isValidDeclaration(tokens.subList(openBlock(tokens), closeBlock(tokens)), parsers) }
            val isElseBlockValid = parsers.any { it.isValidDeclaration(tokens.subList(openBlock(tokens.subList(elseIndex, tokens.size)), tokens.size), parsers) }
            if (!isFirstBlockValid || !isElseBlockValid) throw Exception("could not parse block")
        } else {
            val isBlockValid = parsers.any { it.isValidDeclaration(tokens.subList(2, tokens.size), parsers) }
        }
        if (tokens[tokens.size - 1].type != TokenType.CLOSE_BLOCK) throw Exception("did not open block after else expression")
    }

    private fun openBlock(tokens: List<Token>): Int {
        return tokens.indexOfFirst { it.type == TokenType.OPEN_BLOCK }
    }

    private fun closeBlock(tokens: List<Token>): Int {
        return tokens.indexOfFirst { it.type == TokenType.CLOSE_BLOCK }
    }
    private fun elseIndex(tokens: List<Token>): Int {
        return tokens.indexOfFirst { it.type == TokenType.ELSE }
    }
}
