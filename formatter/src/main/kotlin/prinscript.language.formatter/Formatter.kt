import ast.* //  ktlint-disable no-wildcard-imports
import prinscript.language.formatter.* //  ktlint-disable no-wildcard-imports
import java.io.File

class FormatterImpl(var pathToFile: String, var rules: List<FormattingRule>) : Formatter {

    constructor(pathToFile: String) : this(
        pathToFile,
        listOf(
            SpaceBeforeColon(),
            SpaceAfterColon(),
            SpaceAroundEquals(),
            NewLineBeforeMethod(),
            IndentSize(4),
        ),
    )

    private val newFile: File = File(pathToFile)
    override fun format(ast: AST) {
        var statement = visit(ast)
        statement = "$statement;"
        rules.forEach {
            statement = it.applyRule(statement)
        }
        newFile.appendText("$statement\n")
    }

    private fun visit(ast: AST): String {
        return when (ast) {
            is AssignationAST -> formatAssigation(ast)
            is DeclarationAST -> formatDeclaration(ast)
            is PrintAST -> formatPrint(ast)
            is InputAST -> formatInput(ast)
            is SumAST -> formatSum(ast)
            is SubAST -> formatSub(ast)
            is DivAST -> formatDiv(ast)
            is MulAST -> formatMul(ast)
            is NumberAST -> formatLiteral(ast)
            is StringAST -> formatLiteral(ast)
            is BooleanAST -> formatLiteral(ast)
            is VariableAST -> formatVariable(ast)
            is BlockAST -> formatBlock(ast)
            is IfAST -> formatIf(ast)
            is LiteralInputAST -> formatLiteral(ast)
        }
    }

    private fun formatMul(ast: MulAST): String = "${visit(ast.left)}*${visit(ast.right)}"

    private fun formatDiv(ast: DivAST): String = "${visit(ast.left)}/${visit(ast.right)}"

    private fun formatSub(ast: SubAST): String = "${visit(ast.left)}-${visit(ast.right)}"

    private fun formatSum(ast: SumAST): String = "${visit(ast.left)}+${visit(ast.right)}"

    private fun formatIf(ast: IfAST): String {
        if (ast.elseBlock != null) {
            return(
                "if(${visit(ast.condition)}) ${visit(ast.ifBlock)} else ${visit(ast.elseBlock!!)}"
                )
        }
        return("if(${visit(ast.condition)}) ${visit(ast.ifBlock)}")
    }
    private fun formatBlock(ast: BlockAST): String {
        var statements = ""
        ast.statements.map { statements = "\n\t" + statements + visit(it) + ";" }
        return ("{$statements \n}")
    }

    private fun formatVariable(ast: VariableAST): String = (ast.name)

    private fun formatInput(ast: InputAST): String = "readInput(${visit(ast.inputMsg)})"

    private fun formatPrint(ast: PrintAST): String = "println(${visit(ast.value)})"

    private fun formatDeclaration(ast: DeclarationAST): String {
        if (ast.isConst) {
            return("const ${ast.name}")
        }
        return("let ${ast.name}")
    }

    private fun formatAssigation(ast: AssignationAST): String =
        ("${visit(ast.declaration)}=${visit(ast.expression)}")
    private fun formatLiteral(ast: LiteralAST<*>): String {
        if (ast is StringAST) return "\"${ast.value}\""
        return ("${ast.value}")
    }
}

interface Formatter {
    fun format(ast: AST)
}
