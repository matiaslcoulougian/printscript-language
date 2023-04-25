package printscript.language.parser

class ParserFactory {
    fun createParser(version: String): Parser {
        return when (version) {
            "1.0" -> Parser(
                listOf(
                    DeclarationParser(),
                    PrintParser(),
                    AssignationParser(),
                ),
            )
            "1.1" -> Parser(
                listOf(
                    DeclarationParser(),
                    PrintParser(),
                    IfParser(),
                    BlockParser(),
                    AssignationParser(),
                    ReadInputParser(),
                ),
            )
            else -> throw Exception("Version $version not supported")
        }
    }
}
