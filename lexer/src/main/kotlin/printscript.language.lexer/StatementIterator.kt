package printscript.language.lexer

import java.io.InputStream

class StatementIterator(private val inputStream: InputStream, private val supportBlocks: Boolean) : Iterator<String?> {
    private var currentStatement: String? = null

    override fun hasNext(): Boolean {
        if (currentStatement == null) currentStatement = readStatement()
        return currentStatement != null
    }

    override fun next(): String? {
        var statement = if (currentStatement == null) readStatement() else currentStatement
        currentStatement = null
        val nextStatement = readStatement()
        if (nextStatement != null && nextStatement.trim().startsWith("else")) {
            statement += nextStatement
        } else {
            currentStatement = nextStatement
        }
        return statement
    }

    // Read statement from input stream
    private fun readStatement(): String? {
        var statement = ""
        var depth = 0
        while (true) {
            val byte = inputStream.read()
            if (byte == -1) return null // end of file
            val char = byte.toChar()
            if (char == '\n') { // ignore new lines
                continue
            } else if ((char == ';' && (!supportBlocks || depth == 0))) {
                return statement
            } else if (char == '{' && supportBlocks) {
                depth++
            } else if (char == '}' && supportBlocks) {
                depth--
                if (depth == 0) return statement + char
            }
            statement += char // add char to statement
        }
    }
}
