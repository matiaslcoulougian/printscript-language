package printscript.language.lexer

import java.io.InputStream

class StatementIterator(private val inputStream: InputStream, private val supportBlocks: Boolean) : Iterator<String?> {
    private var nextStatement: String? = null

    override fun hasNext(): Boolean {
        if (nextStatement == null) readNextStatement()
        return nextStatement != null
    }

    override fun next(): String? {
        if (nextStatement == null) readNextStatement()
        val statement = nextStatement
        nextStatement = null
        return statement
    }

    private fun readNextStatement() {
        var statement = ""
        var depth = 0
        while (true) {
            val byte = inputStream.read()
            val char = byte.toChar()
            if (char == '\n') { // ignore new lines
                continue
            } else if ((char == ';' && (!supportBlocks || depth == 0)) || byte == -1) {
                nextStatement = statement
                break // end of statement
            } else if (char == '{' && supportBlocks) {
                depth++
            } else if (char == '}' && supportBlocks) depth--
            statement += char // add char to statement
        }
    }
}
