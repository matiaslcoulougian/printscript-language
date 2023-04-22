package prinscript.language.rule

import ast.AST
import ast.DeclarationAST

object SnakeCaseRule : Rule {

    private val snakeCaseRegex: Regex = Regex("^[a-z_]+\$")
    override fun validate(ast: AST): RuleEvaluation {
        when (ast) {
            is DeclarationAST -> {
                if (!snakeCaseRegex.matches(ast.name)) {
                    return EvaluationFailure("Identifier ${ast.name} is not in snake_case")
                }
            }
        }
        return EvaluationSuccess
    }
}
