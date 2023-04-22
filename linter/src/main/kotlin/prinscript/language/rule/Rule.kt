package prinscript.language.rule

import ast.AST

sealed interface Rule {
    fun validate(ast: AST): RuleEvaluation
}
sealed interface RuleEvaluation {
    fun isValid(): Boolean
}

object EvaluationSuccess : RuleEvaluation {
    override fun isValid(): Boolean = true
}
data class EvaluationFailure(val message: String) : RuleEvaluation {
    override fun isValid(): Boolean = false
}
