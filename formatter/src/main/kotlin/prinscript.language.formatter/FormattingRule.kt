package prinscript.language.formatter

interface FormattingRule {
    fun applyRule(input: String): String
}

class SpaceAfterColon : FormattingRule {
    override fun applyRule(input: String): String {
        return input.replace("\\s*;".toRegex(), "; ")
    }
}
class SpaceBeforeColon : FormattingRule {
    override fun applyRule(input: String): String {
        return input.replace(";".toRegex(), " ;")
    }
}
class SpaceAroundEquals : FormattingRule {
    override fun applyRule(input: String): String {
        return input.replace("=".toRegex(), " = ")
    }
}
class NewLineBeforeMethod() : FormattingRule {
    override fun applyRule(input: String): String {
        val methodCalls = listOf("println", "readInput") // List of method names to consider

        val lines = input.split("\n")
        val result = StringBuilder()

        for (line in lines) {
            val trimmedLine = line.trim()
            val indentation = line.substringBefore(trimmedLine)

            // Check if the line contains a method call
            if (methodCalls.any { trimmedLine.contains(it) }) {
                result.append("$indentation\n")
            }

            result.append(line).append("\n")
        }

        return result.toString().trimEnd() // Trim the trailing newline
    }
}

class IndentSize(private val size: Int) : FormattingRule {
    private val indent = " ".repeat(size)
    override fun applyRule(input: String): String {
        println("HERE $input ")
        println("here ${input.replace("\t", indent)} ")
        return input.replace("\t", indent)
    }
}
