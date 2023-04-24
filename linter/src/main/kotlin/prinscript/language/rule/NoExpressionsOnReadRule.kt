package prinscript.language.rule

import ast.AST
import ast.InputAST
import ast.LiteralAST
import ast.VariableAST

object NoExpressionsOnReadRule : Rule {
    override fun validate(ast: AST): RuleEvaluation {
        return when (ast) {
            is InputAST -> if (ast.inputMsg !is VariableAST && ast.inputMsg !is LiteralAST<*>) {
                return EvaluationFailure(" <${ast.line}:${ast.column}> Input can only be invoked with a variable or literal")
            } else {
                return EvaluationSuccess
            }
            else -> EvaluationSuccess
        }
    }
}
