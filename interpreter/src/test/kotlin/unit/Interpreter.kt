import ast.* // ktlint-disable no-wildcard-imports
import ast.literalAST.NumberAST
import ast.literalAST.StringAST
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import printscript.language.interpreter.interpreter.Interpreter
import printscript.language.interpreter.interpreter.InterpreterImpl

class Interpreter {

    val interpreter: Interpreter = InterpreterImpl()

    @Test
    fun testSum() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
            ),
            SumAST(
                NumberAST(6.0),
                NumberAST(1.0),
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
                Type.NUMBER,
            ),
            MulAST(
                NumberAST(6.0),
                NumberAST(3.0),
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
                Type.NUMBER,
            ),
            SubAST(
                NumberAST(6.0),
                NumberAST(2.0),
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
                Type.NUMBER,
            ),
            DivAST(
                NumberAST(6.0),
                NumberAST(3.0),
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
                        Type.NUMBER,
                    ),
                    DivAST(
                        NumberAST(6.0),
                        NumberAST(3.0),
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
                Type.NUMBER,
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
                Type.NUMBER,
            ),
            SumAST(
                NumberAST(6.0),
                StringAST("1"),
            ),
        )
        assertThrows<Exception> {
            interpreter.interpret(tree)
        }
    }
}
