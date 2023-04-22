package prinscript.language.linter

import ast.* // ktlint-disable no-wildcard-imports
import ast.BooleanAST
import ast.NumberAST
import ast.StringAST
import prinscript.language.rule.EvaluationFailure
import prinscript.language.rule.EvaluationSuccess
import prinscript.language.rule.Rule

class LinterImpl(private val rules: List<Rule>) : Linter {
    override fun lint(ast: AST): List<String> = visit(ast)

    private fun visit(ast: AST): List<String> {
        return when (ast) {
            is AssignationAST -> visit(ast.declaration) + visit(ast.expression) + lintRules(ast)
            is DeclarationAST -> lintRules(ast)
            is PrintAST -> visit(ast) + lintRules(ast)
            is InputAST -> visit(ast) + lintRules(ast)
            is SumAST -> visit(ast.left) + visit(ast.right) + lintRules(ast)
            is SubAST -> visit(ast.left) + visit(ast.right) + lintRules(ast)
            is DivAST -> visit(ast.left) + visit(ast.right) + lintRules(ast)
            is MulAST -> visit(ast.left) + visit(ast.right) + lintRules(ast)
            is NumberAST -> lintRules(ast)
            is StringAST -> lintRules(ast)
            is BooleanAST -> lintRules(ast)
            is VariableAST -> lintRules(ast)
            is BlockAST -> ast.statements.map { visit(it) }.flatten() + lintRules(ast)
            is IfAST -> if (ast.elseBlock != null) {
                visit(ast.condition) + visit(ast.ifBlock) + visit(ast.elseBlock!!) + lintRules(ast)
            } else {
                visit(ast.condition) + visit(ast.ifBlock) + lintRules(ast)
            }
        }
    }

    private fun lintRules(ast: AST): List<String> {
        return rules.mapNotNull { rule ->
            when (val result = rule.validate(ast)) {
                is EvaluationFailure -> result.message
                is EvaluationSuccess -> null
            }
        }
    }
}

interface Linter {
    fun lint(ast: AST): List<String>
}
