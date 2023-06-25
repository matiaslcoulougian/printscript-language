package printscript.language.app

import printscript.language.interpreter.contextProvider.ConsoleContext
import printscript.language.interpreter.interpreter.InterpreterImpl
import printscript.language.interpreter.interpreter.InterpreterWithIterator
import printscript.language.lexer.LexerFactory
import printscript.language.lexer.TokenListIterator
import printscript.language.parser.ASTIterator
import printscript.language.parser.ParserFactory
import java.io.File
import java.io.FileInputStream

fun main() {
    val astIterator = getAstIterator("app/src/main/resources/program_2.txt", "1.1")
    val interpreter = InterpreterWithIterator(InterpreterImpl(ConsoleContext()), astIterator)
    while (interpreter.hasNextInterpretation()) {
        interpreter.interpretNextAST()
    }
//    val interpreter = InterpreterWithIterator(InterpreterImpl(ConsoleContext()), astIterator)
//    val formatter = FormatterImpl("app/src/main/resources/program_formatted.txt")
//    while (astIterator.hasNext()) {
//        formatter.format(astIterator.next() ?: return)
//    }
}

private fun getAstIterator(fileName: String, version: String): ASTIterator {
    val fileContent = File(fileName)
    val lexer = LexerFactory().createLexer(version, FileInputStream(fileContent))
    val tokenListIterator = TokenListIterator(lexer)
    val parser = ParserFactory().createParser(version)
    return ASTIterator(parser, tokenListIterator)
}
