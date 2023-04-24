package prinscript.language.rule

import ast.AST
import ast.LiteralAST
import ast.PrintAST
import ast.VariableAST

object NoExpressionsOnPrintRule : Rule {
    override fun validate(ast: AST): RuleEvaluation {
        return when (ast) {
            is PrintAST -> if (ast.value !is VariableAST && ast.value !is LiteralAST<*>) {
                return EvaluationFailure(" <${ast.line}:${ast.column}> Print can only be invoked with a variable or literal")
            } else {
                return EvaluationSuccess
            }
            else -> EvaluationSuccess
        }
    }
}
