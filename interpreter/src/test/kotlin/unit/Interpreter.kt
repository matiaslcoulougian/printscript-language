import ast.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import printscript.language.interpreter.interpreter.Interpreter
import printscript.language.interpreter.interpreter.InterpreterImpl
import printscript.language.token.TokenType

class Interpreter {

    val interpreter: Interpreter = InterpreterImpl()

    @Test
    fun testSum() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                TokenType.NUMBER_TYPE,
            ),
            SumAST(
                NumberAST(1.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber") == 7.0)
    }

    @Test
    fun testMul() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                TokenType.NUMBER_TYPE,
            ),
            MulAST(
                NumberAST(3.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber") == 18.0)
    }

    @Test
    fun testSub() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                TokenType.NUMBER_TYPE,
            ),
            SubAST(
                NumberAST(2.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber") == 4.0)
    }

    @Test
    fun testDiv() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                TokenType.NUMBER_TYPE,
            ),
            DivAST(
                NumberAST(3.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber") == 2.0)
    }

    @Test
    fun testPrint() {
        // let someNumber: Number = 3*2+1;
        val tree =
            PrintAST(
                AssignationAST(
                    DeclarationAST(
                        "someNumber",
                        TokenType.NUMBER_TYPE,
                    ),
                    DivAST(
                        NumberAST(3.0),
                        NumberAST(6.0),
                    ),
                ),
            )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber") == 2.0)
    }

    @Test
    fun testInvalidTypeAssignationException() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                TokenType.NUMBER_TYPE,
            ),
            StringAST("1"),
        )
        assertThrows<Exception> {
            interpreter.interpret(tree)
        }
    }

    @Test
    fun testInvalidTypeSumException() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                TokenType.NUMBER_TYPE,
            ),
            SumAST(
                StringAST("1"),
                NumberAST(6.0),
            ),
        )
        assertThrows<Exception> {
            interpreter.interpret(tree)
        }
    }
}
