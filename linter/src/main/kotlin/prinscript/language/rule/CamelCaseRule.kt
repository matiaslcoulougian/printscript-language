package prinscript.language.rule

import ast.AST
import ast.DeclarationAST

object CamelCaseRule : Rule {

    private val camelCaseRegex: Regex = Regex("^[a-z]+(?:[A-Z][a-z]+)*\$")
    override fun validate(ast: AST): RuleEvaluation {
        when (ast) {
            is DeclarationAST -> if (!camelCaseRegex.matches(ast.name)) {
                return EvaluationFailure(" <${ast.line}:${ast.column}> Identifier ${ast.name} is not in camelCase ")
            }

            else -> return EvaluationSuccess
        }
        return EvaluationSuccess
    }
}
