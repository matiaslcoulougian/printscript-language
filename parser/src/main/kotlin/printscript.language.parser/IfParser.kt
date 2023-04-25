package printscript.language.parser

import ast.AST
import ast.BlockAST
import ast.BooleanAST
import ast.IfAST
import ast.VariableAST
import printscript.language.token.Token
import printscript.language.token.TokenType

class IfParser : StatementParser {
    override fun isValidStatement(tokens: List<Token>): Boolean {
        return try {
            checkExceptions(tokens)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    override fun parseStatement(tokens: List<Token>): AST {
        val condition: AST = getCondition(tokens)
        val ifBlock: BlockAST = getBlock(tokens)
        val elseIndex = elseIndex(tokens)
        if (elseIndex > 0) {
            val elseBlock: BlockAST = getBlock(tokens.subList(elseIndex, tokens.size))
            return IfAST(condition, ifBlock, elseBlock, tokens[0].line, tokens[0].column)
        }
        return IfAST(condition, ifBlock, tokens[0].line, tokens[0].column)
    }

    private fun getBlock(tokens: List<Token>): BlockAST {
        val from = openBlock(tokens)
        val to = closeBlock(tokens)
        val blockParser: StatementParser = BlockParser()
        return blockParser.parseStatement(tokens.subList(from, to + 1)) as BlockAST
    }

    private fun getCondition(tokens: List<Token>): AST {
        val condition: Token? = tokens.find {
            it.type == TokenType.TRUE ||
                it.type == TokenType.FALSE ||
                it.type == TokenType.IDENTIFIER
        }

        if (condition != null) {
            return when (condition.type) {
                TokenType.IDENTIFIER -> VariableAST(condition.value)
                TokenType.TRUE -> BooleanAST(true)
                TokenType.FALSE -> BooleanAST(false)
                else -> throw Exception("can not parse condition")
            }
        }
        throw Exception("No condition error")
    }

    private fun checkExceptions(tokens: List<Token>) {
        if (tokens[0].type != TokenType.IF) throw Exception("invalid declaration")
        if (tokens[1].type == TokenType.OPEN_PARENTHESIS) {
            if (tokens[2].type != TokenType.VARIABLE &&
                tokens[2].type != TokenType.TRUE &&
                tokens[2].type != TokenType.FALSE
            ) {
                throw Exception("Invalid boolean argument")
            }
            if (tokens[3].type != TokenType.CLOSE_PARENTHESIS) throw Exception("Invalid boolean argument")
            if (tokens[4].type != TokenType.OPEN_BLOCK) { throw Exception("No block for if statement") }
        } else {
            if (tokens[1].type != TokenType.VARIABLE &&
                tokens[1].type != TokenType.TRUE &&
                tokens[1].type != TokenType.FALSE
            ) {
                throw Exception("Invalid boolean argument")
            }
            if (tokens[2].type != TokenType.OPEN_BLOCK) { throw Exception("No block for if statement") }
        }

        val blockParser = BlockParser()
        val ifBlock = tokens.subList(openBlock(tokens), closeBlock(tokens) + 1)
        if (!blockParser.isValidStatement(ifBlock)) throw Exception("Invalid Block")
        val elseIndex = elseIndex(tokens)
        if (elseIndex > 0) { // when the if has else
            if (tokens[elseIndex - 1].type != TokenType.CLOSE_BLOCK) throw Exception("did not close block before else")
            if (tokens[elseIndex + 1].type != TokenType.OPEN_BLOCK) throw Exception("did not open block after else expression")
            val elseBlock = tokens.subList(openBlock(tokens.subList(elseIndex(tokens), tokens.size)) + elseIndex, closeBlock(tokens.subList(elseIndex(tokens), tokens.size)) + elseIndex + 1)
            if (!blockParser.isValidStatement(elseBlock)) throw Exception("Invalid Block")
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
