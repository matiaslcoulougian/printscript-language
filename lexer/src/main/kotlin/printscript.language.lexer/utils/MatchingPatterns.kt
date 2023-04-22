package printscript.language.lexer.utils

// reused regexp patterns to avoid matching values surrounded by quotes ("" or '')

val avoidQuoteEncloseStartPattern = """(?<!['"\w])(?=[^'"\n]*(?:['"][^'"\n]*['"][^'"\n]*)*${'$'})"""
val avoidQuoteEncloseEndPattern = """\w*(?![\w'""])"""
