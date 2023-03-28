import lexer.Lexer

fun main() {
    val lexer = Lexer()
    val tokens = lexer.getAllTokens("let result: number = (1 + 5) - 6;")
    for (token in tokens) {
        println(token.type.name + " " + token.value)
    }
}
