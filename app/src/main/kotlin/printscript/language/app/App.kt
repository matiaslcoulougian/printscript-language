/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package printscript.language.app

import kotlinx.cli.* // ktlint-disable no-wildcard-imports
import printscript.language.interpreter.contextProvider.ConsoleContext
import printscript.language.interpreter.interpreter.InterpreterImpl
import printscript.language.lexer.Lexer
import printscript.language.parser.CompleteParser
import java.io.BufferedReader
import java.io.File

enum class MenuOptions {
    Execution,
}

// TODO: add versioning
fun execute(fileName: String?) {
    val fileContent = File(fileName ?: return)
    val lexer = Lexer()
    val parser = CompleteParser()
    val interpreter = InterpreterImpl(ConsoleContext())
    val reader = BufferedReader(fileContent.reader())
    var statement = ""
    while (true) {
        val char = reader.read()
        if (char == -1) {
            break // end of file
        } else if (char.toChar() == '\n') {
            continue
        } else if (char.toChar() == ';') {
            val tokens = lexer.getTokens(statement)
            val ast = parser.parseLine(tokens)
            interpreter.interpret(ast)
            statement = ""
        } else {
            statement += char.toChar()
        }
    }
}
fun main(args: Array<String>) {
    val parser = ArgParser("printscript")
    val operation by parser.option(
        ArgType.Choice<MenuOptions>(),
        shortName = "o",
        description = "Operation to run",
    ).default(MenuOptions.Execution)
    val input by parser.option(ArgType.String, shortName = "i", description = "Input file")
    val version by parser.option(
        ArgType.Choice(listOf("1.0", "1.1"), { it }),
        shortName = "v",
        description = "Version of program",
    ).default("1.0")
    parser.parse(args)
    when (operation) {
        MenuOptions.Execution -> { execute(input)
        }
    }
}

// "app/src/main/resources/program.txt"
