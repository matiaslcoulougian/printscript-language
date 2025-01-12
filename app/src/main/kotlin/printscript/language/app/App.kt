/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package printscript.language.app

import FormatterImpl
import kotlinx.cli.* // ktlint-disable no-wildcard-imports
import prinscript.language.linter.LinterImpl
import prinscript.language.rule.CamelCaseRule
import prinscript.language.rule.NoExpressionsOnPrintRule
import prinscript.language.rule.NoExpressionsOnReadRule
import printscript.language.interpreter.contextProvider.ConsoleContext
import printscript.language.interpreter.interpreter.InterpreterImpl
import printscript.language.interpreter.interpreter.InterpreterWithIterator
import printscript.language.lexer.LexerFactory
import printscript.language.lexer.TokenListIterator
import printscript.language.parser.ASTIterator
import printscript.language.parser.ParserFactory
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files

enum class MenuOptions {
    Execute,
    Analyze,
    Format,
}

fun execute(fileName: String?, version: String) {
    if (fileName == null) {
        println("No file provided")
        return
    }
    val astIterator = getAstIterator(fileName, version)
    val interpreter = InterpreterWithIterator(InterpreterImpl(ConsoleContext()), astIterator)
    while (interpreter.hasNextInterpretation()) {
        interpreter.interpretNextAST()
    }
}

fun analyze(fileName: String?, version: String) {
    if (fileName == null) {
        println("No file provided")
        return
    }
    val astIterator = getAstIterator(fileName, version)
    val linter = LinterImpl(
        listOf(
            CamelCaseRule,
            NoExpressionsOnPrintRule,
            NoExpressionsOnReadRule,
        ),
    )
    while (astIterator.hasNext()) {
        linter.lint(astIterator.next() ?: return).forEach { println(it) }
    }
}
fun format(fileName: String?, version: String) {
    if (fileName == null) {
        println("No file provided")
        return
    }
    val astIterator = getAstIterator(fileName, version)
    val newFileName = fileName.replace(".txt", "_formatted.txt")
    val formatter = FormatterImpl(newFileName)
    while (astIterator.hasNext()) {
        formatter.format(astIterator.next() ?: return)
    }
    Files.write(File(fileName).toPath(), File(newFileName).readBytes())
    File(newFileName).delete()
}

private fun getAstIterator(fileName: String, version: String): ASTIterator {
    val fileContent = File(fileName)
    val lexer = LexerFactory().createLexer(version, FileInputStream(fileContent))
    val tokenListIterator = TokenListIterator(lexer)
    val parser = ParserFactory().createParser(version)
    return ASTIterator(parser, tokenListIterator)
}

fun main(args: Array<String>) {
    val parser = ArgParser("printscript")
    val operation by parser.option(
        ArgType.Choice<MenuOptions>(),
        shortName = "o",
        description = "Operation to run",
    ).default(MenuOptions.Execute)
    val input by parser.option(ArgType.String, shortName = "i", description = "Input file")
    val version by parser.option(
        ArgType.Choice(listOf("1.0", "1.1"), { it }),
        shortName = "v",
        description = "Version of program",
    ).default("1.0")
    parser.parse(args)
    when (operation) {
        MenuOptions.Execute -> execute(input, version)
        MenuOptions.Analyze -> analyze(input, version)
        MenuOptions.Format -> format(input, version)
    }
}

// "app/src/main/resources/program.txt"
