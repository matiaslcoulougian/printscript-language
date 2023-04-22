package unit

import Type
import ast.* // ktlint-disable no-wildcard-imports
import ast.literalAST.BooleanAST
import ast.literalAST.NumberAST
import ast.literalAST.StringAST
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import printscript.language.interpreter.contextProvider.ConsoleContext
import printscript.language.interpreter.interpreter.Interpreter
import printscript.language.interpreter.interpreter.InterpreterImpl

class Interpreter {

    private val interpreter: Interpreter = InterpreterImpl(ConsoleContext())

    @Test
    fun testSum() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            SumAST(
                NumberAST(1.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber").value == 7.0)
    }

    @Test
    fun testMul() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            MulAST(
                NumberAST(3.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber").value == 18.0)
    }

    @Test
    fun testSub() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            SubAST(
                NumberAST(2.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber").value == 4.0)
    }

    @Test
    fun testDiv() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            DivAST(
                NumberAST(3.0),
                NumberAST(6.0),
            ),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber").value == 2.0)
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
                        false,
                    ),
                    DivAST(
                        NumberAST(3.0),
                        NumberAST(6.0),
                    ),
                ),
            )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber").value == 2.0)
    }

    @Test
    fun testInvalidTypeAssignationException() {
        // let someNumber: Number = 3*2+1;
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            BooleanAST(false),
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
                false,
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

    @Test
    fun testIfElse() {
        // let someNumber: Number = 3*2+1;
        val numberTree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            NumberAST(1.0),
        )
        val booleanAST = AssignationAST(
            DeclarationAST(
                "someBoolean",
                Type.BOOLEAN,
                false,
            ),
            BooleanAST(true),
        )

        val ifTree = IfAST(
            VariableAST("someBoolean"),
            BlockAST(
                listOf(
                    AssignationAST(
                        VariableAST("someNumber"),
                        NumberAST(2.0),
                    ),
                ),
            ),
            BlockAST(
                listOf(
                    AssignationAST(
                        VariableAST("someNumber"),
                        NumberAST(0.0),
                    ),
                ),
            ),
        )

        interpreter.interpret(numberTree)
        interpreter.interpret(booleanAST)
        interpreter.interpret(ifTree)
        assert(interpreter.getMemory().get("someNumber").value == 2.0)
    }

    @Test
    fun testBlockMemoryIsolation() {
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            NumberAST(1.0),
        )

        val blockTree = BlockAST(
            listOf(
                AssignationAST(
                    VariableAST("someNumber"),
                    NumberAST(0.0),
                ),
                AssignationAST(
                    DeclarationAST(
                        "someNumber2",
                        Type.NUMBER,
                        false,
                    ),
                    NumberAST(2.0),
                ),
            ),
        )
        interpreter.interpret(tree)
        interpreter.interpret(blockTree)
        assert(interpreter.getMemory().get("someNumber").value == 0.0)
        assert(interpreter.getMemory().get("someNumber2").type == Type.UNDEFINED)
    }

    @Test
    fun testInput() {
        val tree = AssignationAST(
            DeclarationAST(
                "someNumber",
                Type.NUMBER,
                false,
            ),
            InputAST("enter a number", Type.NUMBER),
        )
        interpreter.interpret(tree)
        assert(interpreter.getMemory().get("someNumber").type == Type.NUMBER)
    }
}
