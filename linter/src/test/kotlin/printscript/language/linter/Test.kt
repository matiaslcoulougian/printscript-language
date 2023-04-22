package printscript.language.linter

import Type
import ast.*
import org.junit.jupiter.api.Test
import prinscript.language.linter.LinterImpl
import prinscript.language.rule.CamelCaseRule
import prinscript.language.rule.NoExpressionsOnPrintRule
import prinscript.language.rule.NoExpressionsOnReadRule
import prinscript.language.rule.SnakeCaseRule

class Test {
    @Test
    fun testNoExpressionOnPrintRule() {
        val tree2 = IfAST(
            BooleanAST(true),
            BlockAST(
                listOf(
                    PrintAST(
                        MulAST(NumberAST(1.0), NumberAST(1.0)),
                    ),
                    PrintAST(
                        SubAST(NumberAST(3.0), NumberAST(2.0)),
                    ),
                ),
            ),
            BlockAST(
                listOf(
                    PrintAST(
                        SumAST(StringAST("Hello"), StringAST("World")),
                    ),
                    PrintAST(
                        StringAST("Hello World"),
                    ),
                ),
            ),
        )
        val linter = LinterImpl(listOf(NoExpressionsOnPrintRule))
        val messages = linter.lint(tree2)
        assert(messages.size == 3)
    }

    @Test
    fun testNoExpressionOnReadRule() {
        val tree2 = IfAST(
            BooleanAST(true),
            BlockAST(
                listOf(
                    InputAST(
                        MulAST(NumberAST(1.0), NumberAST(1.0)),
                        Type.NUMBER,
                    ),
                    InputAST(
                        SubAST(NumberAST(3.0), NumberAST(2.0)),
                        Type.NUMBER,
                    ),
                ),
            ),
            BlockAST(
                listOf(
                    InputAST(
                        SumAST(StringAST("Hello"), StringAST("World")),
                        Type.STRING,
                    ),
                    InputAST(
                        StringAST("Hello World"),
                        Type.STRING,
                    ),
                ),
            ),
        )
        val linter = LinterImpl(listOf(NoExpressionsOnReadRule))
        val messages = linter.lint(tree2)
        assert(messages.size == 3)
    }

    @Test
    fun testCamelCaseRule() {
        val tree = BlockAST(
            listOf(
                DeclarationAST("variableName", Type.STRING, true),
                DeclarationAST("variable_name", Type.STRING, true),
                DeclarationAST("VariableName", Type.STRING, true),
                DeclarationAST("Variable_name", Type.STRING, true),
                DeclarationAST("variablename", Type.STRING, true),
            ),
        )

        val linter = LinterImpl(listOf(CamelCaseRule))
        val messages = linter.lint(tree)
        assert(messages.size == 3)
    }

    @Test
    fun testSnakeCaseRule() {
        val tree = BlockAST(
            listOf(
                DeclarationAST("variableName", Type.STRING, true),
                DeclarationAST("variable_name", Type.STRING, true),
                DeclarationAST("VariableName", Type.STRING, true),
                DeclarationAST("Variable_name", Type.STRING, true),
                DeclarationAST("variablename", Type.STRING, true),
            ),
        )

        val linter = LinterImpl(listOf(SnakeCaseRule))
        val messages = linter.lint(tree)
        println(messages)
        assert(messages.size == 3)
    }
}
