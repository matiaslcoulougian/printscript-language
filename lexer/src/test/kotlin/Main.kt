import printscript.language.lexer.Lexer

fun main() {
    val lexer = Lexer()
    // val tokens = lexer.getTokens("let result: number = (1 + 5) - 6;\nprintln(result);")
    val tokens = lexer.getTokens("const a: boolean = true;\nlet b: boolean = false;\nlet c: boolean = a && b;\nprintln(c);")
    for ((type, value) in tokens) {
        println(type.name + " " + value)
    }
}
